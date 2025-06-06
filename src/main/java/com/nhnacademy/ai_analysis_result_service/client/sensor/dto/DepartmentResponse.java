package com.nhnacademy.ai_analysis_result_service.client.sensor.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class DepartmentResponse {
    private String departmentId;

    private String departmentName;
}
