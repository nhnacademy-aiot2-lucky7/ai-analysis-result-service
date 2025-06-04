package com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.ThresholdDiffAnalysisResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.SummaryGeneratorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThresholdDiffAnalysisSummaryGeneratorTest {

    SummaryGeneratorStrategy<ThresholdDiffAnalysisResult> generator;

    @BeforeEach
    void setup() {
        generator = new ThresholdDiffAnalysisSummaryGenerator();
    }

    @Test
    @DisplayName("지원하는 분석 타입 확인: THRESHOLD_DIFF_ANALYSIS 지원, 다른 타입 미지원")
    void supportsAnalysisTypeTest() {
        assertTrue(generator.supports(AnalysisType.THRESHOLD_DIFF_ANALYSIS));
        assertFalse(generator.supports(AnalysisType.SINGLE_SENSOR_PREDICT));
    }

    @Test
    @DisplayName("ThresholdDiffAnalysisResult DTO를 기반으로 요약 문자열 생성")
    void generateSummaryTest() {
        ThresholdDiffAnalysisResult result = createTestThresholdDiffAnalysisResult();

        String summary = generator.generate(result);

        String expected = "센서 [%d:%s] (%s)의 임계치 변화율 분석 결과"
                .formatted(1, "sensor id", "sensor type");

        assertNotNull(summary);
        assertEquals(expected, summary);
    }

    // 공통 테스트 데이터 생성 메서드
    private ThresholdDiffAnalysisResult createTestThresholdDiffAnalysisResult() {
        SensorInfo sensorInfo = new SensorInfo(1L, "sensor id", "sensor type");
        return new ThresholdDiffAnalysisResult(sensorInfo, null, null, null, null);
    }
}
