package com.nhnacademy.ai_analysis_result_service.common.exception.http;

import com.nhnacademy.ai_analysis_result_service.common.exception.base.CommonHttpException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends CommonHttpException {
    private static final int STATUS_CODE = HttpStatus.FORBIDDEN.value();

    public ForbiddenException(String message) {
        super(STATUS_CODE, message);
    }
}
