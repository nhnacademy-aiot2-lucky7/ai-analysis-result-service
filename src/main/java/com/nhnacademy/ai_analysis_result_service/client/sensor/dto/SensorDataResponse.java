package com.nhnacademy.ai_analysis_result_service.client.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 센서 매핑 번호 조회 결과를 담는 응답 DTO입니다.
 * <p>
 * 주로 {@link com.nhnacademy.ai_analysis_result_service.client.sensor.SensorQueryClient#getMappingNo} 메서드의 반환 타입으로 사용되며,
 * 게이트웨이 ID, 센서 ID, 센서 타입을 기반으로 매핑된 고유 식별 번호를 제공합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataResponse {

    /**
     * 센서 매핑 번호 (sensorDataNo)
     * 분석 결과와 센서 데이터를 연계하기 위한 고유 식별자입니다.
     */
    private Long sensorDataNo;
}