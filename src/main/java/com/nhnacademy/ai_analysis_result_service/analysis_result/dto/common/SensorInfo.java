package com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 개별 센서를 식별하기 위한 정보를 담는 DTO입니다.
 * 게이트웨이 ID, 센서 ID, 센서 타입의 조합으로 고유 센서를 정의합니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SensorInfo {

    /**
     * 센서를 연결한 게이트웨이의 고유 식별자
     */
    @NotBlank(message = "gateway id 값은 비어있을 수 없습니다.")
    private Long gatewayId;

    /**
     * 센서의 고유 식별자
     */
    @NotBlank(message = "sensor id 값은 비어있을 수 없습니다.")
    private String sensorId;

    /**
     * 센서의 타입 (예: 온도, 습도, 진동 등)
     */
    @NotBlank(message = "sensor type 값은 비어있을 수 없습니다.")
    private String sensorType;
}
