package com.nhnacademy.ai_analysis_result_service.common.utils.generator.event.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.ThresholdDiffAnalysisResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.event.strategy.EventDetailGeneratorStrategy;
import org.springframework.stereotype.Component;

@Component
public class ThresholdDiffAnalysisEventDetailGenerator implements EventDetailGeneratorStrategy<ThresholdDiffAnalysisResult> {
    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.THRESHOLD_DIFF_ANALYSIS;
    }

    @Override
    public String generate(ThresholdDiffAnalysisResult dto) {
        return String.format("센서 [%s:%s] (%s)의 임계치 변화율 분석 완료",
                dto.getSensorInfo().getGatewayId(),
                dto.getSensorInfo().getSensorId(),
                dto.getSensorInfo().getSensorType());
    }
}
