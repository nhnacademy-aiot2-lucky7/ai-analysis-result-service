package com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.CorrelationRiskPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.SummaryGeneratorStrategy;
import org.springframework.stereotype.Component;

@Component
public class CorrelationRiskSummaryGenerator implements SummaryGeneratorStrategy<CorrelationRiskPredictResult> {
    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.CORRELATION_RISK_PREDICT;
    }

    @Override
    public String generate(CorrelationRiskPredictResult dto) {
        if (dto.getSensorInfo() == null || dto.getSensorInfo().size() < 2) {
            throw new IllegalArgumentException("상관관계 분석 결과에 센서정보가 존재하지 않습니다.");
        }

        SensorInfo sensor1 = dto.getSensorInfo().getFirst();
        SensorInfo sensor2 = dto.getSensorInfo().getLast();

        return String.format("센서 [%s:%s] (%s) 와 [%s:%s] (%s)의 상관관계 및 위험도 분석 결과",
                sensor1.getGatewayId(), sensor1.getSensorId(), sensor1.getSensorType(),
                sensor2.getGatewayId(), sensor2.getSensorId(), sensor2.getSensorType());
    }
}
