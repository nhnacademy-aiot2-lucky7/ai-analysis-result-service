package com.nhnacademy.ai_analysis_result_service.controller;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.AnalysisResultSaveRequest;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.RecentResultRequest;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.RecentResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import com.nhnacademy.ai_analysis_result_service.analysis_result.service.AnalysisResultService;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.InvalidEnumValueException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analysis-results")
@RequiredArgsConstructor
public class AnalysisResultController {
    private final AnalysisResultService analysisResultService;

    @PostMapping
    ResponseEntity<Void> saveResult(@RequestBody @Valid AnalysisResultSaveRequest<AnalysisResultDto> request) {
        AnalysisResultDto dto = request.getResult();
        AnalysisType type;
        try {
            type = AnalysisType.valueOf(dto.getType());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException("분석 타입이 잘못되었습니다: " + dto.getType());
        }
        analysisResultService.saveAnalysisResult(type, dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/single-sensor-predict-recent-result")
    ResponseEntity<List<RecentResultResponse>> getSingleSensorRecentResult(@RequestBody RecentResultRequest request) {
        List<RecentResultResponse> response = analysisResultService.getSingleSensorPredictRecentPredictedData(request.getSensorInfo());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<AnalysisResultResponse> getResult(@PathVariable Long id) {
        AnalysisResultResponse response = analysisResultService.getAnalysisResult(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public Page<AnalysisResultSearchResponse> searchResults(
            @RequestParam(required = false) AnalysisType analysisType,
            @RequestParam(required = false) String departmentId,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to,
            @RequestParam(required = false) String sensorId,
            @RequestParam(required = false) Long gatewayId,
            @RequestParam(required = false) String sensorType,
            Pageable pageable) {

        // SearchCondition 객체를 수동으로 생성하여 전달
        SearchCondition condition = new SearchCondition(analysisType, departmentId, from, to, sensorId, gatewayId, sensorType);

        return analysisResultService.searchAnalysisResults(condition, pageable);
    }

    @GetMapping("/main/{department-id}")
    public ResponseEntity<List<AnalysisResultResponse>> getLatestTwo(
            @PathVariable("department-id") String departmentId
    ) {
        List<AnalysisResultResponse> list =
                analysisResultService.getMainLatest(departmentId);
        return ResponseEntity.ok(list);
    }
}
