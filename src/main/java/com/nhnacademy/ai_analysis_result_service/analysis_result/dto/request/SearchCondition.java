package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchCondition {
    private AnalysisType analysisType;
    private String departmentId;

    private Long from;
    private Long to;

    private String sensorId;
    private Long gatewayId;
    private String sensorType;
}
