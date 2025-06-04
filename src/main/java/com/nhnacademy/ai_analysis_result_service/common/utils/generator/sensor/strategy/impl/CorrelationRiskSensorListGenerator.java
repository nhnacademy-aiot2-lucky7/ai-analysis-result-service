package com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.CorrelationRiskPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.SensorNotFoundException;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.SensorListGeneratorStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CorrelationRiskSensorListGenerator implements SensorListGeneratorStrategy<CorrelationRiskPredictResult> {
    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.CORRELATION_RISK_PREDICT;
    }

    @Override
    public List<SensorInfo> generate(CorrelationRiskPredictResult dto) {
        try {
            return dto.getSensorInfo();
        } catch (Exception e) {
            throw new SensorNotFoundException(e);
        }
    }
}
