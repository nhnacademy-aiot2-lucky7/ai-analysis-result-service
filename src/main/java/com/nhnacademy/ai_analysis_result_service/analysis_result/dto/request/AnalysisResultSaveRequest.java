package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.request;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class  AnalysisResultSaveRequest<T extends AnalysisResultDto> {
    @Valid
    private T resultDto;
}
