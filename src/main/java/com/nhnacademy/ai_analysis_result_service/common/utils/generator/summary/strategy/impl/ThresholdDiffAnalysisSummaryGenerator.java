package com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.ThresholdDiffAnalysisResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.SummaryGeneratorStrategy;
import org.springframework.stereotype.Component;

@Component
public class ThresholdDiffAnalysisSummaryGenerator implements SummaryGeneratorStrategy<ThresholdDiffAnalysisResult> {
    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.THRESHOLD_DIFF_ANALYSIS;
    }

    @Override
    public String generate(ThresholdDiffAnalysisResult dto) {
        return String.format("센서 [%s:%s] (%s)의 임계치 변화율 분석 결과",
                dto.getSensorInfo().getGatewayId(),
                dto.getSensorInfo().getSensorId(),
                dto.getSensorInfo().getSensorType());
    }
}
