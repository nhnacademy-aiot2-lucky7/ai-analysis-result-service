package com.nhnacademy.ai_analysis_result_service.common.exception.http;

import com.nhnacademy.ai_analysis_result_service.common.exception.base.CommonHttpException;

/**
 * 센서 매핑 번호 조회 중 실패했을 때 발생하는 예외입니다.
 * 주로 외부 센서 서비스(FeignClient) 호출 중 404 또는 기타 오류가 발생한 경우에 사용됩니다.
 *
 * <p>HTTP 상태 코드 404(Not Found)로 반환되며, 예외 메시지는 "센서 조회 실패"입니다.</p>
 */
public class SensorNotFoundException extends CommonHttpException {
    private static final int STATUS_CODE = 404;
    private static final String MESSAGE = "센서 조회 실패";

    /**
     * 센서 조회 실패 예외를 생성합니다.
     *
     * @param cause 내부 원인 예외 (예: FeignException)
     */
    public SensorNotFoundException(Throwable cause){
        super(STATUS_CODE, MESSAGE, cause);
    }
}