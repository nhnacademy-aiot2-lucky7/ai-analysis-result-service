package com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.service;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.meta.strategy.MetaGeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetaGeneratorService {
    private final List<MetaGeneratorStrategy<?>> strategies;

    public <T extends AnalysisResultDto> String generate(AnalysisType type, T dto) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(type))
                .map(strategy -> (MetaGeneratorStrategy<T>) strategy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 분석 타입입니다: " + type))
                .generate(dto);
    }
}
