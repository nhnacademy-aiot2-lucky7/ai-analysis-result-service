package com.nhnacademy.ai_analysis_result_service.common.thread_local.department_id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartmentIdContextHolder {
    private static final ThreadLocal<String> departmentIdHolder = new ThreadLocal<>();

    public static void setDepartmentId(String departmentId) {
        departmentIdHolder.set(departmentId);
    }

    public static String getDepartmentId() {
        return departmentIdHolder.get();
    }

    public static void clear() {
        departmentIdHolder.remove();
    }
}
