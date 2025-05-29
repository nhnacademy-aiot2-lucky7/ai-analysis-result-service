package com.nhnacademy.ai_analysis_result_service.common.exception.http;

import com.nhnacademy.ai_analysis_result_service.common.exception.base.CommonHttpException;
import org.springframework.http.HttpStatus;

public class InvalidEnumValueException extends CommonHttpException {
    private static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public InvalidEnumValueException(String message) {
        super(STATUS_CODE, message);
    }
}
