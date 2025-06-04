package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CorrelationRiskPredictResult implements AnalysisResultDto{
    private final String type = "CORRELATION_RISK_PREDICT";
    private List<SensorInfo> sensorInfo;
    private String model;
    private List<PredictedData> predictedData;
    private Long analyzedAt;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PredictedData {
        private SensorInfo sensorInfo;
        private double singleRiskModel;
        private double correlationRiskModel;
    }
}
