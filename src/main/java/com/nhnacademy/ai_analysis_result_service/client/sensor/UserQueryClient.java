package com.nhnacademy.ai_analysis_result_service.client.sensor;

import com.nhnacademy.ai_analysis_result_service.client.sensor.dto.DepartmentResponse;
import com.nhnacademy.ai_analysis_result_service.client.sensor.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserQueryClient {
    @GetMapping("/users/me")
    UserResponse getUser(@RequestHeader("X-USER-ID") String encryptedEmail);

    @GetMapping("/departments/{departmentId}")
    DepartmentResponse getDepartment(@PathVariable String departmentId);
}