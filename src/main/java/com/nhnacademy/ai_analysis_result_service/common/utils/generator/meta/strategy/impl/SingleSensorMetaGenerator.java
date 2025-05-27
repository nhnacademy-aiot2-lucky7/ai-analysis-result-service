package com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonSerializationException;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.MetaGeneratorStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 분석 결과에 대한 메타 정보를 생성하는 유틸리티 클래스입니다.
 * 분석 타입에 따라 모델 정보, 센서 수, 센서 ID, 기간 등의 정보를 JSON 문자열로 반환합니다.
 */
@Component
public class SingleSensorMetaGenerator implements MetaGeneratorStrategy<SingleSensorPredictResult> {

    protected static final String GATEWAY_ID = "gatewayId";
    protected static final String SENSOR_ID = "sensorId";
    protected static final String SENSOR_TYPE = "sensorType";
    protected static final String MODEL = "model";

    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.SINGLE_SENSOR_PREDICT;
    }

    @Override
    public String generate(SingleSensorPredictResult dto) {
        Map<String, Object> meta = Map.of(
                GATEWAY_ID, dto.getSensorInfo().getGatewayId(),
                SENSOR_ID, dto.getSensorInfo().getSensorId(),
                SENSOR_TYPE, dto.getSensorInfo().getSensorType(),
                MODEL, dto.getModel()
        );

        try {
            return new ObjectMapper().writeValueAsString(meta);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("메타 직렬화 실패", e);
        }
    }
}
