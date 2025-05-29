package com.nhnacademy.ai_analysis_result_service.common.interceptor;

import com.nhnacademy.ai_analysis_result_service.client.sensor.UserQueryClient;
import com.nhnacademy.ai_analysis_result_service.client.sensor.dto.UserResponse;
import com.nhnacademy.ai_analysis_result_service.common.thread_local.department_id.DepartmentIdContextHolder;
import com.nhnacademy.ai_analysis_result_service.common.thread_local.role.RoleContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ContextInterceptor implements HandlerInterceptor {
    private final UserQueryClient userQueryClient;

    public ContextInterceptor(@Lazy UserQueryClient userQueryClient) {
        this.userQueryClient = userQueryClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String encryptedEmail = request.getHeader("X-USER-ID");
        if (encryptedEmail != null) {
            UserResponse user = userQueryClient.getUser(encryptedEmail);
            DepartmentIdContextHolder.setDepartmentId(user.getDepartment().getDepartmentId());
            RoleContextHolder.setRole(user.getUserRole());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        DepartmentIdContextHolder.clear();
        RoleContextHolder.clear();
    }
}
