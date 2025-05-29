package com.nhnacademy.ai_analysis_result_service.analysis_result.repository;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CustomAnalysisResultRepository {
    AnalysisResultResponse findAnalysisResultResponseById(Long id);

    Page<AnalysisResultSearchResponse> searchResults(
            AnalysisType analysisType,
            String departmentId,
            LocalDateTime from,
            LocalDateTime to,
            String sensorId,
            Long gatewayId,
            String sensorType,
            Pageable pageable
    );
}
