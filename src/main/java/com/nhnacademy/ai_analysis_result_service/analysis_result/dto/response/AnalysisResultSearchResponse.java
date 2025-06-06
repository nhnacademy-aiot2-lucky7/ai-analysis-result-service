package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalysisResultSearchResponse {
    private Long id;
    private String departmentName;
    private AnalysisType type;
    private Long analyzedAt;
    private String resultSummary;

    @QueryProjection
    public AnalysisResultSearchResponse(Long id, String departmentName, AnalysisType type, Long analyzedAt, String resultSummary) {
        this.id = id;
        this.departmentName = departmentName;
        this.type = type;
        this.analyzedAt = analyzedAt;
        this.resultSummary = resultSummary;
    }
}
