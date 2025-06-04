package com.nhnacademy.ai_analysis_result_service.common.utils.generator.event.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.CorrelationRiskPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.event.strategy.EventDetailGeneratorStrategy;
import org.springframework.stereotype.Component;

@Component
public class CorrelationRiskEventDetailGenerator implements EventDetailGeneratorStrategy<CorrelationRiskPredictResult> {
    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.CORRELATION_RISK_PREDICT;
    }

    @Override
    public String generate(CorrelationRiskPredictResult dto) {
        SensorInfo sensor1 = dto.getSensorInfo().getFirst();
        SensorInfo sensor2 = dto.getSensorInfo().getLast();

        return String.format("센서 [%s:%s] (%s) 와 [%s:%s] (%s)의 상관관계 및 위험도 분석 완료",
                sensor1.getGatewayId(), sensor1.getSensorId(), sensor1.getSensorType(),
                sensor2.getGatewayId(), sensor2.getSensorId(), sensor2.getSensorType());
    }
}
