package com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.impl.SingleSensorMetaGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({MetaGeneratorService.class, SingleSensorMetaGenerator.class})
class MetaGeneratorServiceTest {
    @Autowired
    MetaGeneratorService metaGeneratorService;

    @Test
    @DisplayName("SINGLE_SENSOR_PREDICT 분석 타입의 메타 정보를 JSON으로 생성한다")
    void generateReturnsCorrectMetaJsonForSingleSensorPredictResult() throws Exception {
        SingleSensorPredictResult result = generateSingleSensorPredictResult();

        AnalysisType type = AnalysisType.SINGLE_SENSOR_PREDICT;

        String meta = metaGeneratorService.generate(type, result);

        assertNotNull(meta);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode expected = objectMapper.readTree("""
                    {
                      "gatewayId": "gateway id",
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
        SingleSensorPredictResult result = generateSingleSensorPredictResult();
        AnalysisType type = null;

        assertThrows(IllegalArgumentException.class, () -> metaGeneratorService.generate(type, result));
    }

    private SingleSensorPredictResult generateSingleSensorPredictResult(){
        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");
        String model = "model";
        List<SingleSensorPredictResult.PredictedData> predictedData = new ArrayList<>();
        LocalDateTime analyzedAt = LocalDateTime.now();

        for (int i = 0; i < 5; i++) {
            SingleSensorPredictResult.PredictedData data = new SingleSensorPredictResult.PredictedData(
                    new Random().nextDouble(10 + i),
                    LocalDateTime.now().toLocalDate()
            );
            predictedData.add(data);
        }

        return new SingleSensorPredictResult(
                sensorInfo,
                model,
                predictedData,
                analyzedAt
        );
    }
}