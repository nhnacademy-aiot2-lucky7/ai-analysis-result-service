package com.nhnacademy.ai_analysis_result_service.client.sensor;

import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.client.sensor.dto.SensorDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 센서 매핑 정보를 조회하기 위한 Feign 클라이언트입니다.
 * SENSOR-SERVICE와의 HTTP 통신을 통해 센서 식별 번호(sensorDataNo) 또는 센서 정보를 조회합니다.
 */
@FeignClient(name = "SENSOR-SERVICE")
public interface SensorQueryClient {

    /**
     * 게이트웨이 ID, 센서 ID, 센서 타입으로 센서 매핑 번호(sensorDataNo)를 조회합니다.
     *
     * @param gatewayId 게이트웨이 ID
     * @param sensorId  센서 ID
     * @param sensorType 센서 타입
     * @return 센서 매핑 번호를 포함한 응답 DTO
     */
    @GetMapping("/sensor-data-mappings/sensor-data-no")
    SensorDataResponse getMappingNo(
            @RequestParam("gatewayId") Long gatewayId,
            @RequestParam("sensorId") String sensorId,
            @RequestParam("sensorType") String sensorType
    );

    /**
     * 센서 매핑 번호(sensorDataNo)를 통해 센서 기본 정보를 조회합니다.
     *
     * @param sensorMappingNo 센서 매핑 번호
     * @return 센서 정보 DTO
     */
    @GetMapping("/sensor-data-mappings/sensor-info/{sensorMappingNo}")
    SensorInfo getSensorInfo(@PathVariable Long sensorMappingNo);
}