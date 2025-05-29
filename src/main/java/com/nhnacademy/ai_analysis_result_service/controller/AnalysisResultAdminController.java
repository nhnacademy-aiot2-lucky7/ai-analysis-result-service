package com.nhnacademy.ai_analysis_result_service.controller;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.service.AnalysisResultAdminService;
import com.nhnacademy.ai_analysis_result_service.common.annotation.CheckRole;
import com.nhnacademy.ai_analysis_result_service.common.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CheckRole(RoleType.ROLE_ADMIN)
@RestController
@RequestMapping("/admin/analysis-result")
@RequiredArgsConstructor
public class AnalysisResultAdminController {
    private final AnalysisResultAdminService analysisResultAdminService;

    @GetMapping("/{id}")
    ResponseEntity<AnalysisResultResponse> getResult(@PathVariable Long id) {
        AnalysisResultResponse response = analysisResultAdminService.getAnalysisResult(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    Page<AnalysisResultSearchResponse> searchResults(SearchCondition condition, Pageable pageable) {
        return analysisResultAdminService.searchAnalysisResults(condition, pageable);
    }
}
