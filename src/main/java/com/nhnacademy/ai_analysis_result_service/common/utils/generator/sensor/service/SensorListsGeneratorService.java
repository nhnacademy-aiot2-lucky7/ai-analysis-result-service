package com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.service;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.SensorListGeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorListsGeneratorService {
    private final List<SensorListGeneratorStrategy<?>> strategies;

    public <T extends AnalysisResultDto> List<SensorInfo> generate(AnalysisType type, T dto) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(type))
                .map(strategy -> (SensorListGeneratorStrategy<T>) strategy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 분석 타입입니다: " + type))
                .generate(dto);
    }
}
