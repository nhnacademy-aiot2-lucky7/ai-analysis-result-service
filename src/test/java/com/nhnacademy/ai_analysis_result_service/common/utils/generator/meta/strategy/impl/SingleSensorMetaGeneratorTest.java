package com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.MetaGeneratorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SingleSensorMetaGeneratorTest {

    MetaGeneratorStrategy<SingleSensorPredictResult> generator;

    @BeforeEach
    void setup() {
        generator = new SingleSensorMetaGenerator();
    }

    @Test
    @DisplayName("단일 센서 예측 타입을 지원하는지 확인")
    void supportsReturnsTrueForSingleSensorPredictType() {
        AnalysisType supportedType = AnalysisType.SINGLE_SENSOR_PREDICT;
        AnalysisType unsupportedType = null;

        boolean result1 = generator.supports(supportedType);
        boolean result2 = generator.supports(unsupportedType);

        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    @DisplayName("단일 센서 DTO를 JSON 메타 문자열로 직렬화한다")
    void generateReturnsCorrectMetaJsonFromDto() throws Exception {
        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");
        String model = "model";

        SingleSensorPredictResult result = new SingleSensorPredictResult(
                sensorInfo,
                model,
                null,
                null
        );

        String metaJson = generator.generate(result);

        assertNotNull(metaJson);
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode expected = objectMapper.readTree("""
                    {
                      "gatewayId": "gateway id",
                      "sensorId": "sensor id",
                      "sensorType": "sensor type",
                      "model": "model"
                    }
                """);

        JsonNode actual = objectMapper.readTree(metaJson);

        assertEquals(expected, actual);
    }
}
