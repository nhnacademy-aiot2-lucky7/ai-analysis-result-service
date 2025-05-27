package com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.service;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.SummaryGeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryGeneratorService {
    private final List<SummaryGeneratorStrategy<?>> strategies;

    public <T extends AnalysisResultDto> String generate(AnalysisType type, T dto){
        return strategies.stream()
                .filter(strategy -> strategy.supports(type))
                .map(strategy -> (SummaryGeneratorStrategy<T>) strategy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 분석 타입입니다: " + type))
                .generate(dto);
    }
}
