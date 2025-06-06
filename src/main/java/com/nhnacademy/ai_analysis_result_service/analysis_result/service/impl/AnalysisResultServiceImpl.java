package com.nhnacademy.ai_analysis_result_service.analysis_result.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.AnalysisResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.RecentResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.repository.AnalysisResultRepository;
import com.nhnacademy.ai_analysis_result_service.analysis_result.service.AnalysisResultService;
import com.nhnacademy.ai_analysis_result_service.client.sensor.GatewayQueryClient;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonDeserializationException;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonSerializationException;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.AnalysisResultNotFoundException;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.ForbiddenException;
import com.nhnacademy.ai_analysis_result_service.common.thread_local.department_id.DepartmentContextHolder;
import com.nhnacademy.ai_analysis_result_service.common.utils.event.service.EventService;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.service.MetaGeneratorService;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.service.SensorListsGeneratorService;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.service.SummaryGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 분석 결과 저장 및 조회를 처리하는 서비스 구현체입니다.
 * 센서 단위 조회, 분석 타입별 조회, 페이징 처리, 결과 요약 기능을 제공합니다.
 * 예외 발생 시 도메인별 예외로 래핑하여 던집니다.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AnalysisResultServiceImpl implements AnalysisResultService {
    private final MetaGeneratorService metaGeneratorService;
    private final SummaryGeneratorService summaryGeneratorService;
    private final SensorListsGeneratorService sensorListsGeneratorService;

    private final EventService eventService;

    private final AnalysisResultRepository analysisResultRepository;
    private final ObjectMapper objectMapper;

    private final GatewayQueryClient gatewayQueryClient;

    @Override
    public <T extends AnalysisResultDto> void saveAnalysisResult(AnalysisType type, T resultDto) {
        String resultJson;
        try {
            resultJson = objectMapper.writeValueAsString(resultDto);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("result 직렬화 중 에러 발생", e);
        }

        Long analyzedAt = resultDto.getAnalyzedAt();
        List<SensorInfo> sensorInfos = sensorListsGeneratorService.generate(type, resultDto);
        String departmentId = gatewayQueryClient.getDepartment(sensorInfos.getFirst().getGatewayId());
        String resultSummary = summaryGeneratorService.generate(type, resultDto);
        String metaJson = metaGeneratorService.generate(type, resultDto);

        AnalysisResult analysisResult = AnalysisResult.of(type, departmentId, analyzedAt, sensorInfos, resultSummary, resultJson, metaJson);
        analysisResultRepository.save(analysisResult);
        eventService.sendCreateResultEvent(type, resultDto, departmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public AnalysisResultResponse getAnalysisResult(Long id) {
        AnalysisResultResponse response = analysisResultRepository.findAnalysisResultResponseById(id);

        if (response == null) {
            throw new AnalysisResultNotFoundException();
        }

        if (!response.getDepartmentId().equals(DepartmentContextHolder.getDepartmentId())) {
            throw new ForbiddenException("결과를 볼 수 있는 권한이 없습니다.");
        }

        return response;
    }

    @Override
    public List<RecentResultResponse> getSingleSensorPredictRecentPredictedData(List<SensorInfo> sensorInfoList) {
        if (sensorInfoList == null || sensorInfoList.isEmpty()) {
            throw new IllegalArgumentException("센서 정보가 없습니다.");
        }

        List<AnalysisResult> analysisResultList = analysisResultRepository.findSingleSensorPredictResult(sensorInfoList);

        return analysisResultList.stream()
                .map(result -> {
                    try {
                        SingleSensorPredictResult singleSensorPredictResult =
                                objectMapper.readValue(result.getResultJson(), SingleSensorPredictResult.class);

                        return new RecentResultResponse(
                                singleSensorPredictResult.getSensorInfo(),
                                singleSensorPredictResult.getPredictedData()
                        );

                    } catch (JsonProcessingException e) {
                        throw new JsonDeserializationException("single sensor result 역직렬화 중 에러 발생", e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalysisResultSearchResponse> searchAnalysisResults(SearchCondition condition, Pageable pageable) {
        if(condition == null){
            return analysisResultRepository.searchResults(
                    null,null,null,null,null,null,null,pageable
            );
        }

        return analysisResultRepository.searchResults(
                condition.getAnalysisType(),
                DepartmentContextHolder.getDepartmentId(),
                condition.getFrom(),
                condition.getTo(),
                condition.getSensorId(),
                condition.getGatewayId(),
                condition.getSensorType(),
                pageable
        );
    }
}