package com.nhnacademy.ai_analysis_result_service.client.sensor.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserResponse {
    String userRole;

    Long userNo;

    String userName;

    String userEmail;

    String userPhone;

    DepartmentResponse department;

    EventLevelResponse eventLevelResponse;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class EventLevelResponse {
        private String eventLevelName;

        private String eventLevelDetails;

        private Integer priority;
    }
}
