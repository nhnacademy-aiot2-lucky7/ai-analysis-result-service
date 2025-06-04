package com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.CorrelationRiskPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonSerializationException;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.MetaGeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.impl.SingleSensorMetaGenerator.MODEL;

@Component
@RequiredArgsConstructor
public class CorrelationRiskMeatGenerator implements MetaGeneratorStrategy<CorrelationRiskPredictResult> {
    protected static final String SENSORS = "sensors";

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.CORRELATION_RISK_PREDICT;
    }

    @Override
    public String generate(CorrelationRiskPredictResult dto) {
        Map<String, Object> meta = Map.of(
                SENSORS, dto.getSensorInfo(),
                MODEL, dto.getModel()
        );

        try {
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("메타 직렬화 실패", e);
        }
    }
}
