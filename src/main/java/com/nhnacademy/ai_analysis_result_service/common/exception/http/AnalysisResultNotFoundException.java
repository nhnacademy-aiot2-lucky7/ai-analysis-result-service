package com.nhnacademy.ai_analysis_result_service.common.exception.http;

import com.nhnacademy.ai_analysis_result_service.common.exception.base.CommonHttpException;

/**
 * 분석 결과가 존재하지 않을 때 발생하는 예외입니다.
 * 주로 ID, 센서 조건, 기간 조건 등의 조회 결과가 비어 있는 경우에 사용됩니다.
 *
 * <p>HTTP 상태 코드 404(Not Found)로 응답되며,
 * 메시지는 "분석 결과가 존재하지 않습니다."입니다.</p>
 */
public class AnalysisResultNotFoundException extends CommonHttpException {
    private static final int STATUS_CODE = 404;
    private static final String MESSAGE = "분석 결과가 존재하지 않습니다.";

    /**
     * 기본 메시지를 포함한 분석 결과 미존재 예외를 생성합니다.
     */
    public AnalysisResultNotFoundException() {
        super(STATUS_CODE, MESSAGE);
    }
}