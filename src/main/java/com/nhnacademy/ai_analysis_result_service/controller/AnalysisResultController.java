package com.nhnacademy.ai_analysis_result_service.controller;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.AnalysisResultSaveRequest;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import com.nhnacademy.ai_analysis_result_service.analysis_result.service.AnalysisResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("analysis-results")
@RequiredArgsConstructor
public class AnalysisResultController {
    private final AnalysisResultService analysisResultService;

    @PostMapping
    ResponseEntity<Void> saveResult(@RequestBody @Valid AnalysisResultSaveRequest<AnalysisResultDto> request) {
        AnalysisResultDto dto = request.getResultDto();
        AnalysisType type = AnalysisType.valueOf(dto.getType());
        analysisResultService.saveAnalysisResult(type, dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
