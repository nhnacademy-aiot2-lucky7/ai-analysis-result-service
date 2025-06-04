package com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.ThresholdDiffAnalysisResult;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonDeserializationException;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonSerializationException;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.MetaGeneratorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThresholdDiffAnalysisMetaGeneratorTest {

    @Spy
    ObjectMapper objectMapper;

    MetaGeneratorStrategy<ThresholdDiffAnalysisResult> generator;
    ThresholdDiffAnalysisResult result;

    @BeforeEach
    void setUp() {
        generator = new ThresholdDiffAnalysisMetaGenerator(objectMapper);

        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");
        String model = "model";
        String meta = """
                    {
                        "analysisStatus": "insufficient_data",
                        "failureReason": "not_enough_days",
                        "actualDataDays": 12,
                        "requiredDataDays": 15,
                        "analysisWindowDays": 15
                    }
                """;

        result = new ThresholdDiffAnalysisResult(
                sensorInfo, model, null, null, meta
        );
    }

    @Test
    @DisplayName("지원하는 분석 타입 확인")
    void supportsReturnsTrueForThresholdDiffAnalysisType() {
        assertTrue(generator.supports(AnalysisType.THRESHOLD_DIFF_ANALYSIS));
        assertFalse(generator.supports(AnalysisType.SINGLE_SENSOR_PREDICT));
    }

    @Test
    @DisplayName("정상적인 메타 데이터 직렬화")
    void generateReturnsCorrectMetaJsonFromDto() throws Exception {
        String metaJson = generator.generate(result);
        assertNotNull(metaJson);

        JsonNode expected = objectMapper.readTree("""
                    {
                      "analysisStatus": "insufficient_data",
                      "failureReason": "not_enough_days",
                      "actualDataDays": 12,
                      "requiredDataDays": 15,
                      "analysisWindowDays": 15,
                      "gatewayId": 1,
                      "sensorId": "sensor id",
                      "sensorType": "sensor type",
                      "model": "model"
                    }
                """);

        JsonNode actual = objectMapper.readTree(metaJson);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("메타 JSON 역직렬화 실패 시 예외 발생")
    void failToDeserializeMetaJson() throws Exception {
        doThrow(JsonProcessingException.class)
                .when(objectMapper)
                .readValue(anyString(), any(TypeReference.class));

        assertThrows(JsonDeserializationException.class, () -> generator.generate(result));
    }

    @Test
    @DisplayName("메타 JSON 직렬화 실패 시 예외 발생")
    void failToSerializeMetaJson() throws Exception {
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        assertThrows(JsonSerializationException.class, () -> generator.generate(result));
    }
}
