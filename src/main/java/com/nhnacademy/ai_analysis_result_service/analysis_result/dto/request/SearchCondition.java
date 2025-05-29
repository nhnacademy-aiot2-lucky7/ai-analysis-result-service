package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchCondition {
    private AnalysisType analysisType;
    private String departmentId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime to;

    private String sensorId;
    private Long gatewayId;
    private String sensorType;
}
