package com.nhnacademy.ai_analysis_result_service.controller;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
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
import org.springframework.web.bind.annotation.*;

@CheckRole(RoleType.ROLE_ADMIN)
@RestController
@RequestMapping("/admin/analysis-results")
@RequiredArgsConstructor
public class AnalysisResultAdminController {
    private final AnalysisResultAdminService analysisResultAdminService;

    @GetMapping("/{id}")
    ResponseEntity<AnalysisResultResponse> getResult(@PathVariable Long id) {
        AnalysisResultResponse response = analysisResultAdminService.getAnalysisResult(id);

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

        return analysisResultAdminService.searchAnalysisResults(condition, pageable);
    }
}
