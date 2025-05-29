package com.nhnacademy.ai_analysis_result_service.controller;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.AnalysisResultSaveRequest;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
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

@RestController
@RequestMapping("/analysis-results")
@RequiredArgsConstructor
public class AnalysisResultController {
    private final AnalysisResultService analysisResultService;

    @PostMapping
    ResponseEntity<Void> saveResult(@RequestBody @Valid AnalysisResultSaveRequest<AnalysisResultDto> request) {
        AnalysisResultDto dto = request.getResultDto();
        AnalysisType type;
        try {
            type = AnalysisType.valueOf(dto.getType());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException("분석 타입이 잘못되었습니다: " + dto.getType());
        }
        analysisResultService.saveAnalysisResult(type, dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<AnalysisResultResponse> getResult(@PathVariable Long id) {
        AnalysisResultResponse response = analysisResultService.getAnalysisResult(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    Page<AnalysisResultSearchResponse> searchResults(SearchCondition condition, Pageable pageable) {
        return analysisResultService.searchAnalysisResults(condition, pageable);
    }
}
