package com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.ThresholdDiffAnalysisResult;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.SensorNotFoundException;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.SensorListGeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ThresholdDiffAnalysisSensorListGenerator implements SensorListGeneratorStrategy<ThresholdDiffAnalysisResult> {
    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.THRESHOLD_DIFF_ANALYSIS;
    }

    @Override
    public List<SensorInfo> generate(ThresholdDiffAnalysisResult dto) {
        try {
            SensorInfo sensorInfo = new SensorInfo(
                    dto.getSensorInfo().getGatewayId(),
                    dto.getSensorInfo().getSensorId(),
                    dto.getSensorInfo().getSensorType()
            );

            return List.of(sensorInfo);
        } catch (Exception e) {
            throw new SensorNotFoundException(e);
        }
    }
}
