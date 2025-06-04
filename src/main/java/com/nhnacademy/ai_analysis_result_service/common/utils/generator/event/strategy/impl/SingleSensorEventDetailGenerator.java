package com.nhnacademy.ai_analysis_result_service.common.utils.generator.event.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.event.strategy.EventDetailGeneratorStrategy;
import org.springframework.stereotype.Component;

/**
 * 분석 결과에 대한 요약 메시지를 생성하는 유틸리티 클래스입니다.
 * 분석 타입에 따라 적절한 요약 문자열을 생성합니다.
 */
@Component
public class SingleSensorEventDetailGenerator implements EventDetailGeneratorStrategy<SingleSensorPredictResult> {

    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.SINGLE_SENSOR_PREDICT;
    }

    @Override
    public String generate(SingleSensorPredictResult dto) {
        return String.format("센서 [%s:%s] (%s)의 예측 분석 완료",
                dto.getSensorInfo().getGatewayId(),
                dto.getSensorInfo().getSensorId(),
                dto.getSensorInfo().getSensorType());
    }
}
