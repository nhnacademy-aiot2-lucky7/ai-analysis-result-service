package com.nhnacademy.ai_analysis_result_service.analysis_result.service.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.repository.AnalysisResultRepository;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.AnalysisResultNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalysisResultAdminServiceImplTest {

    @Mock
    AnalysisResultRepository analysisResultRepository;

    @InjectMocks
    AnalysisResultAdminServiceImpl analysisResultAdminService;

    @Test
    @DisplayName("분석 결과 조회 성공")
    void getAnalysisResultSuccessTest() {
        AnalysisResultResponse response = new AnalysisResultResponse();
        when(analysisResultRepository.findAnalysisResultResponseById(1L)).thenReturn(response);

        AnalysisResultResponse result = analysisResultAdminService.getAnalysisResult(1L);

        assertNotNull(result);
    }

    @Test
    @DisplayName("분석 결과 조회 실패 - 존재하지 않는 ID")
    void getAnalysisResultNotFoundTest() {
        when(analysisResultRepository.findAnalysisResultResponseById(1L)).thenReturn(null);

        assertThrows(AnalysisResultNotFoundException.class, () -> analysisResultAdminService.getAnalysisResult(1L));
    }

    @Test
    @DisplayName("분석 결과 검색 성공")
    void searchAnalysisResultsTest() {
        Page<AnalysisResultSearchResponse> page = new PageImpl<>(List.of());
        when(analysisResultRepository.searchResults(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(page);

        Page<AnalysisResultSearchResponse> result = analysisResultAdminService.searchAnalysisResults(new SearchCondition(), Pageable.unpaged());

        assertNotNull(result);
    }
}