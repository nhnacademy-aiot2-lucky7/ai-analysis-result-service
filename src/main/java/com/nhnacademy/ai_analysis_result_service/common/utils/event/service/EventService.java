package com.nhnacademy.ai_analysis_result_service.common.utils.event.service;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;

public interface EventService {
    <T extends AnalysisResultDto> void sendCreateResultEvent(AnalysisType type, T dto, String departmentId);
}
