package com.nhnacademy.ai_analysis_result_service.analysis_result.service;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnalysisResultAdminService {
    AnalysisResultResponse getAnalysisResult(Long id);

    Page<AnalysisResultSearchResponse> searchAnalysisResults(SearchCondition condition, Pageable pageable);
}
