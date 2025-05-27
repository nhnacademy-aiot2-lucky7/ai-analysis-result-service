package com.nhnacademy.ai_analysis_result_service.common.exception.global;

import com.nhnacademy.ai_analysis_result_service.common.exception.base.JsonProcessingFailedException;

/**
 * 객체를 JSON 문자열로 직렬화하는 과정에서 실패할 경우 발생하는 예외입니다.
 * Jackson의 {@link com.fasterxml.jackson.core.JsonProcessingException}을 감싸며,
 * 직렬화 과정에서의 구체적인 원인을 전달합니다.
 *
 * <p>예: 분석 결과 DTO를 JSON 문자열로 변환하는 과정에서 오류 발생 시</p>
 */
public class JsonSerializationException extends JsonProcessingFailedException {

    /**
     * 메시지와 원인 예외를 포함한 직렬화 실패 예외를 생성합니다.
     *
     * @param message 상세 오류 메시지
     * @param cause   내부 발생 예외 (예: JsonProcessingException)
     */
    public JsonSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}