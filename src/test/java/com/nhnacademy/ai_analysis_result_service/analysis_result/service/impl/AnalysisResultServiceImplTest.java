package com.nhnacademy.ai_analysis_result_service.analysis_result.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.AnalysisResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.repository.AnalysisResultRepository;
import com.nhnacademy.ai_analysis_result_service.client.sensor.SensorQueryClient;
import com.nhnacademy.ai_analysis_result_service.client.sensor.dto.SensorDataResponse;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonSerializationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
class AnalysisResultServiceImplTest {
    @Autowired
    AnalysisResultServiceImpl analysisResultService;

    @MockitoBean
    AnalysisResultRepository analysisResultRepository;

    @MockitoBean
    SensorQueryClient sensorQueryClient;

    @MockitoSpyBean
    ObjectMapper objectMapper;

    @Test
    @DisplayName("SINGLE_SENSOR_PREDICT 분석 결과 저장 시 Repository의 save가 호출된다")
    void saveCallsRepositorySaveWhenValidInputGiven() {
        SensorDataResponse sensorDataResponse = new SensorDataResponse(1L);

        when(sensorQueryClient.getMappingNo(anyString(), anyString(), anyString())).thenReturn(sensorDataResponse);

        AnalysisType type = AnalysisType.SINGLE_SENSOR_PREDICT;
        SingleSensorPredictResult result = generateSingleSensorPredictResult();

        analysisResultService.saveAnalysisResult(type, result);

        verify(analysisResultRepository, times(1)).save(any(AnalysisResult.class));
    }

    @Test
    @DisplayName("ObjectMapper 직렬화 실패 시 JsonSerializationException을 던진다")
    void saveThrowsJsonSerializationExceptionWhenObjectMapperFails() throws Exception {
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        AnalysisType type = AnalysisType.SINGLE_SENSOR_PREDICT;
        SingleSensorPredictResult result = generateSingleSensorPredictResult();

        assertThrows(JsonSerializationException.class, () -> analysisResultService.saveAnalysisResult(type, result));
    }

    private SingleSensorPredictResult generateSingleSensorPredictResult() {
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