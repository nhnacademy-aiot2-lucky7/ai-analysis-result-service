package com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.client.sensor.SensorQueryClient;
import com.nhnacademy.ai_analysis_result_service.client.sensor.dto.SensorDataResponse;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.SensorListGeneratorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SingleSensorListGeneratorTest {
    SensorListGeneratorStrategy<SingleSensorPredictResult> generator;

    @BeforeEach
    void setup() {
        generator = new SingleSensorListGenerator();
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
    @DisplayName("SensorInfo를 기반으로 센서 매핑 번호 리스트를 생성한다")
    void generateReturnsSensorDataNoListFromSensorInfo() {
        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");

        SingleSensorPredictResult result = new SingleSensorPredictResult(
                sensorInfo,
                null,
                null,
                null
        );

        List<SensorInfo> sensorList = generator.generate(result);

        assertNotNull(sensorList);
        assertEquals(1, sensorList.size());
        assertEquals(sensorInfo.getGatewayId(), sensorList.getFirst().getGatewayId());
        assertEquals(sensorInfo.getSensorId(), sensorList.getFirst().getSensorId());
        assertEquals(sensorInfo.getSensorType(), sensorList.getFirst().getSensorType());
    }
}