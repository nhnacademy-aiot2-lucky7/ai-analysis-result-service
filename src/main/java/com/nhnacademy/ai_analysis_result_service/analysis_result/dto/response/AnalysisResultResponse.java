package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnalysisResultResponse {
    private Long id;
    private String departmentId;
    private String resultJson;

    @QueryProjection
    public AnalysisResultResponse(Long id, String departmentId, String resultJson) {
        this.id = id;
        this.departmentId = departmentId;
        this.resultJson = resultJson;
    }
}
