package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecentResultResponse {
    SensorInfo sensorInfo;
    List<SingleSensorPredictResult.PredictedData> data;
}
