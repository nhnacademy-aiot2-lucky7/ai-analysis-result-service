package com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.service;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.client.sensor.SensorQueryClient;
import com.nhnacademy.ai_analysis_result_service.client.sensor.dto.SensorDataResponse;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.impl.SingleSensorListGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({SensorListsGeneratorService.class, SingleSensorListGenerator.class})
class SensorListsGeneratorServiceTest {
    @Autowired
    SensorListsGeneratorService sensorListsGeneratorService;

    @MockitoBean
    SensorQueryClient sensorQueryClient;

    @Test
    @DisplayName("SINGLE_SENSOR_PREDICT 타입 DTO를 기반으로 센서 매핑 번호 리스트를 생성한다")
    void generateReturnsSensorDataListFromSingleSensorPredictResult() {
        SingleSensorPredictResult result = generateSingleSensorPredictResult();

        AnalysisType type = AnalysisType.SINGLE_SENSOR_PREDICT;

        SensorDataResponse response = new SensorDataResponse(1L);
        when(sensorQueryClient.getMappingNo(anyString(), anyString(), anyString())).thenReturn(response);

        List<Long> sensorList = sensorListsGeneratorService.generate(type, result);

        assertNotNull(sensorList);
        assertEquals(1,sensorList.size());
        assertEquals(1L, sensorList.getFirst());
    }

    @Test
    @DisplayName("지원되지 않는 분석 타입(null)일 경우 예외를 던진다")
    void generateThrowsExceptionWhenAnalysisTypeIsNull() {
        SingleSensorPredictResult result = generateSingleSensorPredictResult();
        AnalysisType type = null;

        assertThrows(IllegalArgumentException.class, () -> sensorListsGeneratorService.generate(type, result));
    }

    private SingleSensorPredictResult generateSingleSensorPredictResult(){
        SensorInfo sensorInfo = new SensorInfo("gateway id", "sensor id", "sensor type");
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