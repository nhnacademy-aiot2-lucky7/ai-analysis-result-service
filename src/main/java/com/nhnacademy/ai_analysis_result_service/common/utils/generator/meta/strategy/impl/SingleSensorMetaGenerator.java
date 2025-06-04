package com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonSerializationException;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.MetaGeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * {@link SingleSensorPredictResult} 분석 결과를 기반으로 메타 데이터를 생성하는 클래스입니다.
 * <p>
 * 지원하는 분석 타입이 {@code SINGLE_SENSOR_PREDICT}인 경우에 동작하며,
 * 게이트웨이 ID, 센서 ID, 센서 타입, 모델 정보를 포함한 메타 정보를 JSON 문자열로 생성합니다.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class SingleSensorMetaGenerator implements MetaGeneratorStrategy<SingleSensorPredictResult> {

    protected static final String GATEWAY_ID = "gatewayId";
    protected static final String SENSOR_ID = "sensorId";
    protected static final String SENSOR_TYPE = "sensorType";
    protected static final String MODEL = "model";

    private final ObjectMapper objectMapper;

    /**
     * 해당 전략이 지원하는 분석 타입인지 여부를 반환합니다.
     *
     * @param type 분석 타입
     * @return 지원하는 경우 {@code true}, 그렇지 않은 경우 {@code false}
     */
    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.SINGLE_SENSOR_PREDICT;
    }

    /**
     * 주어진 {@link SingleSensorPredictResult}로부터 메타 데이터를 생성하여 JSON 문자열로 반환합니다.
     *
     * @param dto 분석 결과 DTO
     * @return 생성된 메타 데이터(JSON 문자열)
     * @throws JsonSerializationException JSON 직렬화에 실패한 경우 발생
     */
    @Override
    public String generate(SingleSensorPredictResult dto) {
        Map<String, Object> meta = Map.of(
                GATEWAY_ID, dto.getSensorInfo().getGatewayId(),
                SENSOR_ID, dto.getSensorInfo().getSensorId(),
                SENSOR_TYPE, dto.getSensorInfo().getSensorType(),
                MODEL, dto.getModel()
        );

        try {
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("메타 직렬화 실패", e);
        }
    }
}
