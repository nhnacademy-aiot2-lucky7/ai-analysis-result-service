package com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.service;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.ThresholdDiffAnalysisResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SummaryGeneratorServiceTest {

    @Autowired
    SummaryGeneratorService summaryGeneratorService;

    @Test
    @DisplayName("SINGLE_SENSOR_PREDICT 타입 - 정상 생성")
    void generateSuccessfullyForSingleSensorPredict() {
        SingleSensorPredictResult result = createSingleSensorPredictResult();

        String summary = assertDoesNotThrow(() ->
                summaryGeneratorService.generate(AnalysisType.SINGLE_SENSOR_PREDICT, result)
        );

        String expected = "센서 [%d:%s] (%s)의 예측 분석 결과"
                .formatted(1, "sensor id", "sensor type");

        assertNotNull(summary);
        assertEquals(expected, summary);
    }

    @Test
    @DisplayName("THRESHOLD_DIFF_ANALYSIS 타입 - 정상 생성")
    void generateSuccessfullyForThresholdDiffAnalysis() {
        ThresholdDiffAnalysisResult result = createThresholdDiffAnalysisResult();

        String summary = assertDoesNotThrow(() ->
                summaryGeneratorService.generate(AnalysisType.THRESHOLD_DIFF_ANALYSIS, result)
        );

        String expected = "센서 [%d:%s] (%s)의 임계치 변화율 분석 결과"
                .formatted(1, "sensor id", "sensor type");

        assertNotNull(summary);
        assertEquals(expected, summary);
    }

    @Test
    @DisplayName("지원되지 않는 분석 타입(null)일 경우 예외 발생")
    void generateThrowsExceptionWhenTypeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> summaryGeneratorService.generate(null, null));
    }

    // --------- 헬퍼 메서드 ---------

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
}
