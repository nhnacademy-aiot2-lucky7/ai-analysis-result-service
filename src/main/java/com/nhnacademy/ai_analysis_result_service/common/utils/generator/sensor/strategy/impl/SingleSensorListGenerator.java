package com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.client.sensor.SensorQueryClient;
import com.nhnacademy.ai_analysis_result_service.client.sensor.dto.SensorDataResponse;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.SensorNotFoundException;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.sensor.strategy.SensorListGeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SingleSensorListGenerator implements SensorListGeneratorStrategy<SingleSensorPredictResult> {
    private final SensorQueryClient client;

    @Override
    public boolean supports(AnalysisType type) {
        return type == AnalysisType.SINGLE_SENSOR_PREDICT;
    }

    @Override
    public List<Long> generate(SingleSensorPredictResult dto) {
        try {
            SensorDataResponse response = client.getMappingNo(
                    dto.getSensorInfo().getGatewayId(),
                    dto.getSensorInfo().getSensorId(),
                    dto.getSensorInfo().getSensorType()
            );
            return List.of(response.getSensorDataNo());
        } catch (Exception e) {
            throw new SensorNotFoundException(e);
        }
    }
}
