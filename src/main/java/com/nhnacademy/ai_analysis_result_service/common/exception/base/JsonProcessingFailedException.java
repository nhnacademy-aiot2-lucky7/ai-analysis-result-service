package com.nhnacademy.ai_analysis_result_service.common.exception.base;

import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonDeserializationException;
import com.nhnacademy.ai_analysis_result_service.common.exception.global.JsonSerializationException;

/**
 * JSON 처리 과정에서 발생하는 예외의 상위 클래스입니다.
 * 직렬화 또는 역직렬화 실패 시 하위 예외에서 이 클래스를 상속받아 사용합니다.
 *
 * <p>일반적으로 Jackson 처리 중 발생한 {@link com.fasterxml.jackson.core.JsonProcessingException}을 감싸며,
 * 구체적인 예외는 {@link JsonSerializationException}, {@link JsonDeserializationException}에서 구분됩니다.</p>
 */
public class JsonProcessingFailedException extends RuntimeException {

    /**
     * 메시지와 원인 예외를 포함하는 JSON 처리 실패 예외를 생성합니다.
     *
     * @param message 오류 메시지
     * @param cause   내부 발생 예외
     */
    public JsonProcessingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}