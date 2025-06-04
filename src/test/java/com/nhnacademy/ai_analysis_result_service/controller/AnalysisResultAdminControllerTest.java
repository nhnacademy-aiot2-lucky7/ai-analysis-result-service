package com.nhnacademy.ai_analysis_result_service.controller;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.service.AnalysisResultAdminService;
import com.nhnacademy.ai_analysis_result_service.common.enums.RoleType;
import com.nhnacademy.ai_analysis_result_service.common.thread_local.role.RoleContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnalysisResultAdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AnalysisResultAdminService analysisResultAdminService;

    @BeforeEach
    void setUp() {
        RoleContextHolder.setRole(RoleType.ROLE_ADMIN.getValue());
    }

    @Test
    @DisplayName("단건 조회 성공 (권한 포함)")
    void getResult_success_withRole() throws Exception {
        AnalysisResultResponse response = new AnalysisResultResponse();
        Mockito.when(analysisResultAdminService.getAnalysisResult(1L)).thenReturn(response);

        mockMvc.perform(get("/admin/analysis-result/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("검색 조회 성공 (권한 포함)")
    void searchResults_success_withRole() throws Exception {
        AnalysisResultSearchResponse searchResponse = new AnalysisResultSearchResponse();
        Mockito.when(analysisResultAdminService.searchAnalysisResults(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(searchResponse)));

        mockMvc.perform(get("/admin/analysis-result/search"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("권한 없음 - Forbidden 발생 확인")
    void forbiddenWhenRoleIsNotAdmin() throws Exception {
        RoleContextHolder.setRole("ROLE_USER");

        mockMvc.perform(get("/admin/analysis-result/1"))
                .andExpect(status().isForbidden());
    }
}