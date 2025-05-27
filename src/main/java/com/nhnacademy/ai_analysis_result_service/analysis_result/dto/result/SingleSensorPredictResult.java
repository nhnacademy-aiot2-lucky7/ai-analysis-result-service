package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 단일 센서에 대한 이상 감지 분석 결과를 나타내는 DTO입니다.
 * 예측값, 실제값, 잔차, 신뢰 구간 등을 포함하며, 분석 시점과 이상 여부도 함께 기록합니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleSensorPredictResult implements AnalysisResultDto {
    private final String type = "SINGLE_SENSOR_PREDICT";
    private SensorInfo sensorInfo;
    private String model;
    private List<PredictedData> predictedData;
    private LocalDateTime analyzedAt;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PredictedData {
        private double predictedValue;
        private LocalDate predictedDate;
    }
}
