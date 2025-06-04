package com.nhnacademy.ai_analysis_result_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.AnalysisResultSaveRequest;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.service.AnalysisResultService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnalysisResultController.class)
class AnalysisResultControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AnalysisResultService analysisResultService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("분석 결과 저장 성공")
    void saveResultSuccess() throws Exception {
        SingleSensorPredictResult dto = createTestDto();
        AnalysisResultSaveRequest<AnalysisResultDto> request = new AnalysisResultSaveRequest<>(dto);

        mockMvc.perform(post("/analysis-results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("분석 결과 저장 실패 - 잘못된 타입 전달")
    void saveResultInvalidType() throws Exception {
        SingleSensorPredictResult validDto = new SingleSensorPredictResult(
                new SensorInfo(1L, "sensorId", "sensorType"),
                "model", null, 123L
        );

        String validJson = objectMapper.writeValueAsString(new AnalysisResultSaveRequest<>(validDto));

        String invalidJson = validJson.replace("SINGLE_SENSOR_PREDICT", "INVALID_TYPE");

        mockMvc.perform(post("/analysis-results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("분석 결과 단건 조회 성공")
    void getResultSuccess() throws Exception {
        AnalysisResultResponse response = new AnalysisResultResponse();
        Mockito.when(analysisResultService.getAnalysisResult(anyLong())).thenReturn(response);

        mockMvc.perform(get("/analysis-results/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("분석 결과 검색 성공")
    void searchResultsSuccess() throws Exception {
        Mockito.when(analysisResultService.searchAnalysisResults(any(SearchCondition.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/analysis-results/search"))
                .andExpect(status().isOk());
    }

    private SingleSensorPredictResult createTestDto() {
        return new SingleSensorPredictResult(new SensorInfo(1L, "sensorId", "sensorType"),
                "model", null, 123L);
    }
}