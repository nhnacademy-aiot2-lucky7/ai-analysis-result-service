package com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.ThresholdDiffAnalysisResult;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.SensorNotFoundException;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.SensorListGeneratorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ThresholdDiffAnalysisSensorListGeneratorTest {

    private SensorListGeneratorStrategy<ThresholdDiffAnalysisResult> generator;

    @BeforeEach
    void setUp() {
        generator = new ThresholdDiffAnalysisSensorListGenerator();
    }

    @Test
    @DisplayName("THRESHOLD_DIFF_ANALYSIS 타입 지원 확인")
    void supports_ReturnsTrueForThresholdDiffAnalysis() {
        assertTrue(generator.supports(AnalysisType.THRESHOLD_DIFF_ANALYSIS));
    }

    @Test
    @DisplayName("지원하지 않는 타입에 대해 false 반환")
    void supports_ReturnsFalseForUnsupportedType() {
        assertFalse(generator.supports(AnalysisType.SINGLE_SENSOR_PREDICT));
    }

    @Test
    @DisplayName("SensorInfo 기반 센서 리스트 정상 생성")
    void generate_ReturnsSensorList() {
        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");
        ThresholdDiffAnalysisResult result = new ThresholdDiffAnalysisResult(sensorInfo, null, null, null, null);

        List<SensorInfo> sensorList = generator.generate(result);

        assertNotNull(sensorList);
        assertEquals(1, sensorList.size());

        SensorInfo generatedSensor = sensorList.getFirst();
        assertEquals(sensorInfo.getGatewayId(), generatedSensor.getGatewayId());
        assertEquals(sensorInfo.getSensorId(), generatedSensor.getSensorId());
        assertEquals(sensorInfo.getSensorType(), generatedSensor.getSensorType());
    }

    @Test
    @DisplayName("SensorInfo가 null일 경우 예외 발생")
    void generate_ThrowsExceptionWhenSensorInfoIsNull() {
        ThresholdDiffAnalysisResult result = new ThresholdDiffAnalysisResult(null, null, null, null, null);

        assertThrows(SensorNotFoundException.class, () -> generator.generate(result));
    }
}
