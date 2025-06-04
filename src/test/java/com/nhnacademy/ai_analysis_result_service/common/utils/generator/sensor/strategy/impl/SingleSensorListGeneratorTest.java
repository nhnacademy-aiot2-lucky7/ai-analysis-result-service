package com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
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
class SingleSensorListGeneratorTest {

    private SensorListGeneratorStrategy<SingleSensorPredictResult> generator;

    @BeforeEach
    void setUp() {
        generator = new SingleSensorListGenerator();
    }

    @Test
    @DisplayName("SINGLE_SENSOR_PREDICT 타입을 지원하는지 확인")
    void supports_ReturnsTrueForSupportedType() {
        assertTrue(generator.supports(AnalysisType.SINGLE_SENSOR_PREDICT));
    }

    @Test
    @DisplayName("다른 AnalysisType은 지원하지 않는지 확인")
    void supports_ReturnsFalseForUnsupportedType() {
        assertFalse(generator.supports(AnalysisType.THRESHOLD_DIFF_ANALYSIS));
    }

    @Test
    @DisplayName("SensorInfo 기반 센서 리스트 생성 성공")
    void generate_ReturnsSensorListFromSensorInfo() {
        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");
        SingleSensorPredictResult result = new SingleSensorPredictResult(sensorInfo, null, null, null);

        List<SensorInfo> sensorList = generator.generate(result);

        assertNotNull(sensorList);
        assertEquals(1, sensorList.size());

        SensorInfo generatedSensor = sensorList.getFirst();
        assertEquals(sensorInfo.getGatewayId(), generatedSensor.getGatewayId());
        assertEquals(sensorInfo.getSensorId(), generatedSensor.getSensorId());
        assertEquals(sensorInfo.getSensorType(), generatedSensor.getSensorType());
    }

    @Test
    @DisplayName("SensorInfo가 없을 경우 SensorNotFoundException 발생")
    void generate_ThrowsExceptionWhenSensorInfoIsNull() {
        SingleSensorPredictResult result = new SingleSensorPredictResult(null, null, null, null);

        assertThrows(SensorNotFoundException.class, () -> generator.generate(result));
    }
}
