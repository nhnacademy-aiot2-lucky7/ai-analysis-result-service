package com.nhnacademy.ai_analysis_result_service.common.thread_local.department_id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartmentContextHolder {
    private static final ThreadLocal<String> departmentIdHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> departmentNameHolder = new ThreadLocal<>();

    public static void setDepartmentId(String departmentId) {
        departmentIdHolder.set(departmentId);
    }
    public static void setDepartmentName(String departmentName) {
        departmentNameHolder.set(departmentName);
    }

    public static String getDepartmentId() {
        return departmentIdHolder.get();
    }
    public static String getDepartmentName() {
        return departmentNameHolder.get();
    }

    public static void clear() {
        departmentIdHolder.remove();
        departmentNameHolder.remove();
    }
}
