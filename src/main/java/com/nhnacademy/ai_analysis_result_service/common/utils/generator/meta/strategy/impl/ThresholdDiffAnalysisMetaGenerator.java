package com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.ThresholdDiffAnalysisResult;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonDeserializationException;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonSerializationException;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.MetaGeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.impl.SingleSensorMetaGenerator.*;

@Component
@RequiredArgsConstructor
public class ThresholdDiffAnalysisMetaGenerator implements MetaGeneratorStrategy<ThresholdDiffAnalysisResult> {
    private static final TypeReference<Map<String, Object>> META_TYPE = new TypeReference<>() {
    };
    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.THRESHOLD_DIFF_ANALYSIS;
    }

    @Override
    public String generate(ThresholdDiffAnalysisResult dto) {
        Map<String, Object> meta;
        try {
            meta = objectMapper.readValue(dto.getMeta(), META_TYPE);
        } catch (JsonProcessingException e) {
            throw new JsonDeserializationException("threshold diff analysis metadata 역직렬화 중 오류 발생", e);
        }

        meta.put(GATEWAY_ID, dto.getSensorInfo().getGatewayId());
        meta.put(SENSOR_ID, dto.getSensorInfo().getSensorId());
        meta.put(SENSOR_TYPE, dto.getSensorInfo().getSensorType());
        meta.put(MODEL, dto.getModel());

        try {
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("threshold diff analysis metadata 직렬화 중 오류 발생", e);
        }
    }
}
