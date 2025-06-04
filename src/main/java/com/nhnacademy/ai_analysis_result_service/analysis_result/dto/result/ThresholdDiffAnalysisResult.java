package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThresholdDiffAnalysisResult implements AnalysisResultDto {
    private final String type = "THRESHOLD_DIFF_ANALYSIS";
    private SensorInfo sensorInfo;
    private String model;
    private Double healthScore;
    private Long analyzedAt;
    private String meta;
}
