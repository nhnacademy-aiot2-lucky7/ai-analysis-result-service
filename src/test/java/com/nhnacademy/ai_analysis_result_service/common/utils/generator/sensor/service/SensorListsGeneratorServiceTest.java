package com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.service;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.ThresholdDiffAnalysisResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SensorListsGeneratorServiceTest {

    @Autowired
    SensorListsGeneratorService sensorListsGeneratorService;

    @Test
    @DisplayName("SINGLE_SENSOR_PREDICT 타입 - 정상 생성")
    void generateSuccessfullyForSingleSensorPredict() {
        SingleSensorPredictResult result = createSingleSensorPredictResult();

        assertDoesNotThrow(() -> {
            List<SensorInfo> generatedList = sensorListsGeneratorService.generate(AnalysisType.SINGLE_SENSOR_PREDICT, result);
            assertNotNull(generatedList);
            assertEquals(1, generatedList.size());
            assertSensorInfoEquals(result.getSensorInfo(), generatedList.getFirst());
        });
    }

    @Test
    @DisplayName("THRESHOLD_DIFF_ANALYSIS 타입 - 정상 생성")
    void generateSuccessfullyForThresholdDiffAnalysis() {
        ThresholdDiffAnalysisResult result = createThresholdDiffAnalysisResult();

        assertDoesNotThrow(() -> {
            List<SensorInfo> generatedList = sensorListsGeneratorService.generate(AnalysisType.THRESHOLD_DIFF_ANALYSIS, result);
            assertNotNull(generatedList);
            assertEquals(1, generatedList.size());
            assertSensorInfoEquals(result.getSensorInfo(), generatedList.getFirst());
        });
    }

    @Test
    @DisplayName("지원되지 않는 분석 타입(null)일 경우 예외 발생")
    void generateThrowsExceptionWhenTypeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> sensorListsGeneratorService.generate(null, null));
    }

    // --------- 헬퍼 메서드들 ---------

    private SingleSensorPredictResult createSingleSensorPredictResult() {
        SensorInfo sensorInfo = createTestSensorInfo();
        return new SingleSensorPredictResult(sensorInfo, null, null, null);
    }

    private ThresholdDiffAnalysisResult createThresholdDiffAnalysisResult() {
        SensorInfo sensorInfo = createTestSensorInfo();
        return new ThresholdDiffAnalysisResult(sensorInfo, null, null, null, null);
    }

    private SensorInfo createTestSensorInfo() {
        return new SensorInfo(1L, "sensor id", "sensor type");
    }

    private void assertSensorInfoEquals(SensorInfo expected, SensorInfo actual) {
        assertEquals(expected.getGatewayId(), actual.getGatewayId());
        assertEquals(expected.getSensorId(), actual.getSensorId());
        assertEquals(expected.getSensorType(), actual.getSensorType());
    }
}
