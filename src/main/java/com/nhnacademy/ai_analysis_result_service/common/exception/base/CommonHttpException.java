package com.nhnacademy.ai_analysis_result_service.common.exception.base;

import lombok.Getter;

/**
 * HTTP 상태 코드와 메시지를 포함하는 공통 HTTP 예외 클래스입니다.
 * 도메인별 HTTP 예외들은 이 클래스를 상속받아 상태 코드와 메시지를 일관되게 관리할 수 있습니다.
 *
 * <p>예외가 발생했을 때 응답 상태 코드와 함께 의미 있는 메시지를 제공하기 위한 목적입니다.</p>
 *
 * <p>대표 상속 예:
 * <ul>
 *     <li>{@link com.nhnacademy.ai_analysis_result_service.common.exception.http.SensorNotFoundException}</li>
 *     <li>{@link com.nhnacademy.ai_analysis_result_service.common.exception.http.AnalysisResultNotFoundException}</li>
 * </ul>
 * </p>
 */
@Getter
public class CommonHttpException extends RuntimeException {

    /**
     * HTTP 응답 상태 코드 (예: 404, 400, 500 등)
     */
    private final int statusCode;

    /**
     * 메시지와 상태 코드를 포함하는 HTTP 예외 생성자
     *
     * @param statusCode HTTP 상태 코드
     * @param message    예외 메시지
     */
    public CommonHttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * 메시지, 상태 코드, 내부 원인을 포함하는 HTTP 예외 생성자
     *
     * @param statusCode HTTP 상태 코드
     * @param message    예외 메시지
     * @param cause      내부 원인 예외
     */
    public CommonHttpException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}