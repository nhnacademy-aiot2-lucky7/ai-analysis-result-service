package com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.SummaryGeneratorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleSensorSummaryGeneratorTest {

    SummaryGeneratorStrategy<SingleSensorPredictResult> generator;

    @BeforeEach
    void setup() {
        generator = new SingleSensorSummaryGenerator();
    }

    @Test
    @DisplayName("지원하는 분석 타입 확인: SINGLE_SENSOR_PREDICT 지원, 다른 타입 미지원")
    void supportsAnalysisTypeTest() {
        assertTrue(generator.supports(AnalysisType.SINGLE_SENSOR_PREDICT));
        assertFalse(generator.supports(AnalysisType.THRESHOLD_DIFF_ANALYSIS));
    }

    @Test
    @DisplayName("SingleSensorPredictResult DTO를 기반으로 요약 문자열 생성")
    void generateSummaryTest() {
        SingleSensorPredictResult result = createTestSingleSensorPredictResult();

        String summary = generator.generate(result);

        String expected = "센서 [%d:%s] (%s)의 예측 분석 결과"
                .formatted(1, "sensor id", "sensor type");

        assertNotNull(summary);
        assertEquals(expected, summary);
    }

    // 공통 테스트 데이터 생성 메서드
    private SingleSensorPredictResult createTestSingleSensorPredictResult() {
        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");
        return new SingleSensorPredictResult(sensorInfo, null, null, null);
    }
}
