package com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;

import java.util.List;

public interface SensorListGeneratorStrategy<T extends AnalysisResultDto> {
    boolean supports(AnalysisType type);

    List<SensorInfo> generate(T dto);
}
