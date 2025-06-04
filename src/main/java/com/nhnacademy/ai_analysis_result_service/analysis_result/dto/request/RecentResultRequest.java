package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request;


import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecentResultRequest {
    List<SensorInfo> sensorInfo;
}
