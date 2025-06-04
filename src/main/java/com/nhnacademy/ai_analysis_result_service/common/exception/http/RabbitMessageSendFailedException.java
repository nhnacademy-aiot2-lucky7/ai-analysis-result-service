package com.nhnacademy.ai_analysis_result_service.common.exception.http;

import com.nhnacademy.ai_analysis_result_service.common.exception.base.CommonHttpException;

public class RabbitMessageSendFailedException extends CommonHttpException {
    private static final int HTTP_STATUS_CODE = 500;

    public RabbitMessageSendFailedException() {
        super(HTTP_STATUS_CODE, "RabbitMQ 메시지 전송에 실패했습니다.");
    }

    public RabbitMessageSendFailedException(String message) {
        super(HTTP_STATUS_CODE, message);
    }
}
