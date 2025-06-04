package com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.CorrelationRiskPredictResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.ThresholdDiffAnalysisResult;
import lombok.Getter;

/**
 * AI 분석 결과의 타입을 정의하는 열거형입니다.
 * 단일 센서 이상 감지 또는 센서 간 관계 기반 이상 감지를 나타냅니다.
 * 각 타입에 따라 매핑되는 결과 DTO 클래스가 존재합니다.
 */
@Getter
public enum AnalysisType {

    /**
     * 단일 센서 기반 이상 감지 (예: 시계열 예측 기반)
     */
    SINGLE_SENSOR_PREDICT(SingleSensorPredictResult.class),
    THRESHOLD_DIFF_ANALYSIS(ThresholdDiffAnalysisResult.class),
    CORRELATION_RISK_PREDICT(CorrelationRiskPredictResult.class);

    /**
     * 해당 분석 타입에 대응하는 DTO 클래스
     */
    private final Class<? extends AnalysisResultDto> resultClass;

    AnalysisType(Class<? extends AnalysisResultDto> resultClass) {
        this.resultClass = resultClass;
    }
}