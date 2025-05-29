package com.nhnacademy.ai_analysis_result_service.common.thread_local.role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleContextHolder {
    private static final ThreadLocal<String> roleHolder = new ThreadLocal<>();

    public static void setRole(String departmentId){
        roleHolder.set(departmentId);
    }

    public static String getRole(){
        return roleHolder.get();
    }

    public static void clear(){
        roleHolder.remove();
    }
}
