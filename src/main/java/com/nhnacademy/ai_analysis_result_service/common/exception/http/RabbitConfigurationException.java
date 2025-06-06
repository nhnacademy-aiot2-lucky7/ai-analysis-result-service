package com.nhnacademy.ai_analysis_result_service.common.exception.http;

import com.nhnacademy.ai_analysis_result_service.common.exception.base.CommonHttpException;

public class RabbitConfigurationException extends CommonHttpException {

    private static final int HTTP_STATUS_CODE = 500;

    public RabbitConfigurationException() {
        super(HTTP_STATUS_CODE, "RabbitMQ 구성 요소 설정에 실패했습니다.");
    }

    public RabbitConfigurationException(final String message) {
        super(HTTP_STATUS_CODE, message);
    }
}
