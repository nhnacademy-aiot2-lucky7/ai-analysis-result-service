package com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MetaGeneratorServiceTest {
    @Autowired
    MetaGeneratorService metaGeneratorService;

    @Test
    @DisplayName("SINGLE_SENSOR_PREDICT 분석 타입의 메타 정보를 JSON으로 생성한다")
    void generateReturnsCorrectMetaJsonForSingleSensorPredictResult() throws Exception {
        SingleSensorPredictResult result = createTestSingleSensorPredictResult();

        String meta = metaGeneratorService.generate(AnalysisType.SINGLE_SENSOR_PREDICT, result);

        assertNotNull(meta);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expected = objectMapper.readTree("""
                    {
                      "gatewayId": 1,
                      "sensorId": "sensor id",
                      "sensorType": "sensor type",
                      "model": "model"
                    }
                """);

        JsonNode actual = objectMapper.readTree(meta);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("지원되지 않는 분석 타입(null)일 경우 예외를 던진다")
    void generateThrowsExceptionWhenAnalysisTypeIsNull() {
        SingleSensorPredictResult result = createTestSingleSensorPredictResult();

        assertThrows(IllegalArgumentException.class, () -> metaGeneratorService.generate(null, result));
    }

    private SingleSensorPredictResult createTestSingleSensorPredictResult() {
        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");
        String model = "model";
        List<SingleSensorPredictResult.PredictedData> predictedData = new ArrayList<>();
        Long analyzedAt = LocalDateTime.now()
                .atZone(ZoneId.of("Asia/Seoul"))
                .toInstant()
                .toEpochMilli();

        for (int i = 0; i < 5; i++) {
            double value = 10 + i + new Random().nextDouble();
            long timestamp = LocalDateTime.now()
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .toInstant()
                    .toEpochMilli();
            predictedData.add(new SingleSensorPredictResult.PredictedData(value, timestamp));
        }

        return new SingleSensorPredictResult(sensorInfo, model, predictedData, analyzedAt);
    }
}
