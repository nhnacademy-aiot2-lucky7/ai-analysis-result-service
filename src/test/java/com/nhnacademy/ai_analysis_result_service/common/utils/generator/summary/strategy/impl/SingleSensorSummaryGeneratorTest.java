package com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.SummaryGeneratorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SingleSensorSummaryGeneratorTest {
    SummaryGeneratorStrategy<SingleSensorPredictResult> generator;

    @BeforeEach
    void setup() {
        generator = new SingleSensorSummaryGenerator();
    }

    @Test
    @DisplayName("단일 센서 예측 타입을 지원하는지 확인")
    void supportsReturnsTrueForSingleSensorPredictType() {
        AnalysisType supportedType = AnalysisType.SINGLE_SENSOR_PREDICT;
        AnalysisType unsupportedType = null;

        boolean result1 = generator.supports(supportedType);
        boolean result2 = generator.supports(unsupportedType);

        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    @DisplayName("SingleSensorPredictResult DTO를 기반으로 요약 문자열을 생성한다")
    void generateReturnsSummaryFromSingleSensorPredictResult() {
        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");
        LocalDateTime analyzedAt = LocalDateTime.now();

        SingleSensorPredictResult result = new SingleSensorPredictResult(
                sensorInfo,
                null,
                null,
                analyzedAt
        );

        String summary = generator.generate(result);

        String expected = "센서 [%s:%s] (%s)의 예측 분석 결과 (%s)"
                .formatted("gateway id", "sensor id", "sensor type", analyzedAt.toLocalDate());

        assertNotNull(summary);
        assertEquals(expected, summary);
    }
}