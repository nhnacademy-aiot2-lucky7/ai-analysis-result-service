package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnalysisResultSearchResponse {
    private Long id;
    private String departmentId;
    private AnalysisType type;
    private Long analyzedAt;
    private String resultSummary;

    @QueryProjection
    public AnalysisResultSearchResponse(Long id, String departmentId, AnalysisType type, Long analyzedAt, String resultSummary) {
        this.id = id;
        this.departmentId = departmentId;
        this.type = type;
        this.analyzedAt = analyzedAt;
        this.resultSummary = resultSummary;
    }
}
