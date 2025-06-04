package com.nhnacademy.ai_analysis_result_service.analysis_result.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.AnalysisResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.repository.AnalysisResultRepository;
import com.nhnacademy.ai_analysis_result_service.client.sensor.GatewayQueryClient;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.AnalysisResultNotFoundException;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.ForbiddenException;
import com.nhnacademy.ai_analysis_result_service.common.thread_local.department_id.DepartmentIdContextHolder;
import com.nhnacademy.ai_analysis_result_service.common.utils.event.service.EventService;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.service.MetaGeneratorService;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.service.SensorListsGeneratorService;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.service.SummaryGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalysisResultServiceImplTest {

    @Mock MetaGeneratorService metaGeneratorService;
    @Mock SummaryGeneratorService summaryGeneratorService;
    @Mock SensorListsGeneratorService sensorListsGeneratorService;
    @Mock
    EventService eventService;
    @Mock AnalysisResultRepository analysisResultRepository;
    @Mock ObjectMapper objectMapper;
    @Mock GatewayQueryClient gatewayQueryClient;

    @InjectMocks
    AnalysisResultServiceImpl analysisResultService;

    private static final String TEST_DEPARTMENT = "test-dept";

    @BeforeEach
    void setUp() {
        DepartmentIdContextHolder.clear();
    }

    @Test
    @DisplayName("분석 결과 저장 성공")
    void saveAnalysisResultTest() throws JsonProcessingException {
        SingleSensorPredictResult dto = createTestDto();

        when(objectMapper.writeValueAsString(dto)).thenReturn("result-json");
        when(sensorListsGeneratorService.generate(AnalysisType.SINGLE_SENSOR_PREDICT, dto))
                .thenReturn(List.of(new SensorInfo(1L, "sensorId", "sensorType")));
        when(gatewayQueryClient.getDepartment(1L)).thenReturn(TEST_DEPARTMENT);
        when(summaryGeneratorService.generate(AnalysisType.SINGLE_SENSOR_PREDICT, dto)).thenReturn("summary");
        when(metaGeneratorService.generate(AnalysisType.SINGLE_SENSOR_PREDICT, dto)).thenReturn("meta");

        assertDoesNotThrow(() -> analysisResultService.saveAnalysisResult(AnalysisType.SINGLE_SENSOR_PREDICT, dto));
        verify(analysisResultRepository).save(any(AnalysisResult.class));
    }

    @Test
    @DisplayName("분석 결과 조회 성공")
    void getAnalysisResultFoundTest() throws Exception {
        AnalysisResultResponse response = new AnalysisResultResponse();
        Field did = response.getClass().getDeclaredField("departmentId");
        did.setAccessible(true);
        did.set(response, TEST_DEPARTMENT);
        when(analysisResultRepository.findAnalysisResultResponseById(1L)).thenReturn(response);

        DepartmentIdContextHolder.setDepartmentId(TEST_DEPARTMENT);

        AnalysisResultResponse result = analysisResultService.getAnalysisResult(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("분석 결과 조회 - 존재하지 않는 ID")
    void getAnalysisResultNotFoundTest() {
        when(analysisResultRepository.findAnalysisResultResponseById(1L)).thenReturn(null);
        assertThrows(AnalysisResultNotFoundException.class, () -> analysisResultService.getAnalysisResult(1L));
    }

    @Test
    @DisplayName("분석 결과 조회 - 권한 불일치")
    void getAnalysisResultForbiddenTest() throws Exception {
        AnalysisResultResponse response = new AnalysisResultResponse();
        Field did = response.getClass().getDeclaredField("departmentId");
        did.setAccessible(true);
        did.set(response, "not_test_department");
        when(analysisResultRepository.findAnalysisResultResponseById(1L)).thenReturn(response);

        DepartmentIdContextHolder.setDepartmentId(TEST_DEPARTMENT);

        assertThrows(ForbiddenException.class, () -> analysisResultService.getAnalysisResult(1L));
    }

    @Test
    @DisplayName("분석 결과 검색 성공")
    void searchAnalysisResultsTest() {
        Page<AnalysisResultSearchResponse> page = new PageImpl<>(List.of());
        when(analysisResultRepository.searchResults(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(page);

        DepartmentIdContextHolder.setDepartmentId(TEST_DEPARTMENT);

        Page<AnalysisResultSearchResponse> result = analysisResultService.searchAnalysisResults(new SearchCondition(), Pageable.unpaged());
        assertNotNull(result);
    }

    // ----------- 더미 DTO 생성 -----------
    private SingleSensorPredictResult createTestDto() {
        SensorInfo sensorInfo = new SensorInfo(1L, "sensorId", "sensorType");
        return new SingleSensorPredictResult(sensorInfo, "model", null, 123L);
    }
}
