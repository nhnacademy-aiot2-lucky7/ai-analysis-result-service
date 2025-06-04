package com.nhnacademy.ai_analysis_result_service.common.aspect;

import com.nhnacademy.ai_analysis_result_service.common.annotation.CheckRole;
import com.nhnacademy.ai_analysis_result_service.common.exception.http.ForbiddenException;
import com.nhnacademy.ai_analysis_result_service.common.thread_local.role.RoleContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleCheckAspect {
    @Before("@within(checkRole)")
    public void checkRoleOnClass(CheckRole checkRole) {
        verifyRole(checkRole.value().getValue());
    }

    @Before("@annotation(checkRole)")
    public void checkRoleOnMethod(CheckRole checkRole) {
        verifyRole(checkRole.value().getValue());
    }

    private void verifyRole(String required) {
        String actual = RoleContextHolder.getRole();
        if (!required.equals(actual)) {
            throw new ForbiddenException("권한 없음");
        }
    }
}