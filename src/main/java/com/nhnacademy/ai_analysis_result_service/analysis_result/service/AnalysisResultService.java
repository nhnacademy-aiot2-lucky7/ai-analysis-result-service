package com.nhnacademy.ai_analysis_result_service.analysis_result.service;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request.SearchCondition;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 분석 결과 저장 및 조회를 담당하는 서비스 인터페이스.
 * 센서 식별은 {@link SensorInfo}를 기준으로 수행된다.
 */
public interface AnalysisResultService {
    /**
     * 분석 결과를 저장한다. 분석 결과 DTO는 {@link AnalysisResultDto}를 구현해야 하며,
     * 내부적으로 resultJson, resultSummary, metaJson 등으로 가공되어 저장된다.
     *
     * @param type      분석 타입
     * @param resultDto 분석 결과 DTO
     * @param <T>       분석 결과 DTO 타입
     */
    <T extends AnalysisResultDto> void saveAnalysisResult(AnalysisType type, T resultDto);

    AnalysisResultResponse getAnalysisResult(Long id);

    Page<AnalysisResultSearchResponse> searchAnalysisResults(SearchCondition condition, Pageable pageable);
}