package com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.service;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.summary.strategy.impl.SingleSensorSummaryGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({SummaryGeneratorService.class, SingleSensorSummaryGenerator.class})
class SummaryGeneratorServiceTest {
    @Autowired
    SummaryGeneratorService summaryGeneratorService;

    @Test
    @DisplayName("SINGLE_SENSOR_PREDICT 타입 DTO를 기반으로 요약 메시지를 생성한다")
    void generateReturnsSummaryFromSingleSensorPredictResult() {
        SingleSensorPredictResult result = generateSingleSensorPredictResult();
        AnalysisType type = AnalysisType.SINGLE_SENSOR_PREDICT;

        String summary = summaryGeneratorService.generate(type, result);

        String expected = "센서 [%s:%s] (%s)의 예측 분석 결과 (%s)"
                .formatted("gateway id", "sensor id", "sensor type", result.getAnalyzedAt().toLocalDate());

        assertNotNull(summary);
        assertEquals(expected, summary);
    }

    @Test
    @DisplayName("지원되지 않는 분석 타입(null)일 경우 예외를 던진다")
    void generateThrowsExceptionWhenAnalysisTypeIsNull() {
        SingleSensorPredictResult result = generateSingleSensorPredictResult();
        AnalysisType type = null;

        assertThrows(IllegalArgumentException.class, () -> summaryGeneratorService.generate(type, result));
    }

    private SingleSensorPredictResult generateSingleSensorPredictResult(){
        SensorInfo sensorInfo = new SensorInfo("gateway id", "sensor id", "sensor type");
        String model = "model";
        List<SingleSensorPredictResult.PredictedData> predictedData = new ArrayList<>();
        LocalDateTime analyzedAt = LocalDateTime.now();

        for (int i = 0; i < 5; i++) {
            SingleSensorPredictResult.PredictedData data = new SingleSensorPredictResult.PredictedData(
                    new Random().nextDouble(10 + i),
                    LocalDateTime.now().toLocalDate()
            );
            predictedData.add(data);
        }

        return new SingleSensorPredictResult(
                sensorInfo,
                model,
                predictedData,
                analyzedAt
        );
    }
}