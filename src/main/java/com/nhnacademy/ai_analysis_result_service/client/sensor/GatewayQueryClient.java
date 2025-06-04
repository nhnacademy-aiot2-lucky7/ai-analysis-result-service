package com.nhnacademy.ai_analysis_result_service.client.sensor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("gateway-service")
public interface GatewayQueryClient {
    @GetMapping("/gateways/{gateway-id}/department-id")
    String getDepartment(@PathVariable("gateway-id") Long gatewayId);
}
