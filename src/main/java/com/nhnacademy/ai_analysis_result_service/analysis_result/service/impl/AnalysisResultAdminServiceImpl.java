package com.nhnacademy.ai_analysis_result_service.analysis_result.service.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.repository.AnalysisResultRepository;
import com.nhnacademy.ai_analysis_result_service.analysis_result.service.AnalysisResultAdminService;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.AnalysisResultNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnalysisResultAdminServiceImpl implements AnalysisResultAdminService {
    private final AnalysisResultRepository analysisResultRepository;

    @Override
    public AnalysisResultResponse getAnalysisResult(Long id) {
        AnalysisResultResponse response = analysisResultRepository.findAnalysisResultResponseById(id);

        if (response == null) {
            throw new AnalysisResultNotFoundException();
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalysisResultSearchResponse> searchAnalysisResults(SearchCondition condition, Pageable pageable) {
        return analysisResultRepository.searchResults(
                condition.getAnalysisType(),
                condition.getDepartmentId(),
                condition.getFrom(),
                condition.getTo(),
                condition.getSensorId(),
                condition.getGatewayId(),
                condition.getSensorType(),
                pageable
        );
    }
}
