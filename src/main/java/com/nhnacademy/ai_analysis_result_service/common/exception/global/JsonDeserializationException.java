package com.nhnacademy.ai_analysis_result_service.common.exception.global;

import com.nhnacademy.ai_analysis_result_service.common.exception.base.JsonProcessingFailedException;

/**
 * JSON 문자열을 객체로 역직렬화하는 과정에서 실패할 경우 발생하는 예외입니다.
 * Jackson의 {@link com.fasterxml.jackson.core.JsonProcessingException}을 감싸며,
 * 역직렬화 오류의 상세 원인을 전달합니다.
 *
 * <p>예: 분석 결과 JSON을 DTO로 변환하는 과정에서 오류가 발생했을 때</p>
 */
public class JsonDeserializationException extends JsonProcessingFailedException {

    /**
     * 메시지와 원인 예외를 포함한 역직렬화 실패 예외를 생성합니다.
     *
     * @param message 상세 오류 메시지
     * @param cause   내부 발생 예외 (예: JsonProcessingException)
     */
    public JsonDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }
}