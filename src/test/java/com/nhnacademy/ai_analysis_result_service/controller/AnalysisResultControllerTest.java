package com.nhnacademy.ai_analysis_result_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.AnalysisResultSaveRequest;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.service.AnalysisResultService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class AnalysisResultControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AnalysisResultService analysisResultService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /analysis-results 요청으로 분석 결과를 저장한다")
    void saveAnalysisResultSuccessfully() throws Exception {
        AnalysisResultSaveRequest<SingleSensorPredictResult> request = getSingleSensorPredictResult();

        mockMvc.perform(post("/analysis-results")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(analysisResultService, times(1)).saveAnalysisResult(
                eq(AnalysisType.SINGLE_SENSOR_PREDICT),
                argThat(dto -> dto instanceof SingleSensorPredictResult
                               && ((SingleSensorPredictResult) dto).getSensorInfo().getSensorId().equals("sensor id"))
        );
    }

    private AnalysisResultSaveRequest<SingleSensorPredictResult> getSingleSensorPredictResult() {
        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");
        String model = "model";
        List<SingleSensorPredictResult.PredictedData> predictedData = new ArrayList<>();
        LocalDateTime analyzedAt = LocalDateTime.now();

        for (int i = 0; i < 5; i++) {
            predictedData.add(new SingleSensorPredictResult.PredictedData(
                    new Random().nextDouble(10 + i),
                    LocalDateTime.now().toLocalDate()
            ));
        }

        SingleSensorPredictResult result = new SingleSensorPredictResult(
                sensorInfo,
                model,
                predictedData,
                analyzedAt
        );

        return new AnalysisResultSaveRequest<>(result);
    }
}