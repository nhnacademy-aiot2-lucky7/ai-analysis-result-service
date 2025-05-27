package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

/**
 * 분석 결과 DTO의 공통 인터페이스입니다.
 * 이상 감지 여부와 분석 시점을 제공해야 합니다.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SingleSensorPredictResult.class, name = "SINGLE_SENSOR_PREDICT")
})
public interface AnalysisResultDto {
    String getType();
    /**
     * 분석이 수행된 시점을 반환합니다.
     *
     * @return 분석 시각
     */
    LocalDateTime getAnalyzedAt();
}