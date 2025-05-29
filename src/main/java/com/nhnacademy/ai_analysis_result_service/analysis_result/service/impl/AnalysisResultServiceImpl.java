package com.nhnacademy.ai_analysis_result_service.analysis_result.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.AnalysisResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import com.nhnacademy.ai_analysis_result_service.analysis_result.repository.AnalysisResultRepository;
import com.nhnacademy.ai_analysis_result_service.analysis_result.service.AnalysisResultService;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonSerializationException;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.AnalysisResultNotFoundException;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.ForbiddenException;
import com.nhnacademy.ai_analysis_result_service.common.thread_local.department_id.DepartmentIdContextHolder;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.service.MetaGeneratorService;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.service.SensorListsGeneratorService;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.service.SummaryGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    private final AnalysisResultRepository analysisResultRepository;
    private final ObjectMapper objectMapper;

    @Override
    public <T extends AnalysisResultDto> void saveAnalysisResult(AnalysisType type, T resultDto){
        String resultJson;
        try{
            resultJson = objectMapper.writeValueAsString(resultDto);
        }catch (JsonProcessingException e){
            throw new JsonSerializationException("result 직렬화 중 에러 발생", e);
        }

        String departmentId = DepartmentIdContextHolder.getDepartmentId();
        LocalDateTime analyzedAt = resultDto.getAnalyzedAt();
        List<SensorInfo> sensorInfos = sensorListsGeneratorService.generate(type, resultDto);
        String resultSummary = summaryGeneratorService.generate(type, resultDto);
        String metaJson = metaGeneratorService.generate(type, resultDto);

        AnalysisResult analysisResult = AnalysisResult.of(type, departmentId, analyzedAt, sensorInfos, resultSummary, resultJson, metaJson);
        analysisResultRepository.save(analysisResult);
    }

    @Override
    public AnalysisResultResponse getAnalysisResult(Long id) {
        AnalysisResultResponse response = analysisResultRepository.findAnalysisResultResponseById(id);

        if(response == null){
            throw new AnalysisResultNotFoundException();
        }

        if(!response.getDepartmentId().equals(DepartmentIdContextHolder.getDepartmentId())){
            throw new ForbiddenException("결과를 볼 수 있는 권한이 없습니다.");
        }

        return response;
    }

    @Override
    public Page<AnalysisResultSearchResponse> searchAnalysisResults(SearchCondition condition, Pageable pageable) {
        return analysisResultRepository.searchResults(
                condition.getAnalysisType(),
                DepartmentIdContextHolder.getDepartmentId(),
                condition.getFrom(),
                condition.getTo(),
                condition.getSensorId(),
                condition.getGatewayId(),
                condition.getSensorType(),
                pageable
        );
    }
}