package com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;

public interface MetaGeneratorStrategy<T extends AnalysisResultDto> {
    boolean supports(AnalysisType type);
    String generate(T dto);
}
