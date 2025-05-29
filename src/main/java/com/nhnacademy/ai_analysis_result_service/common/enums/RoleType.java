package com.nhnacademy.ai_analysis_result_service.common.enums;

public enum RoleType {
    ROLE_MEMBER("ROLE_MEMBER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
