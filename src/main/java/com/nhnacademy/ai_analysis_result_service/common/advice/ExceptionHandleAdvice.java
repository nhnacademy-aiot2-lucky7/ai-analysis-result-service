package com.nhnacademy.ai_analysis_result_service.common.advice;

import com.nhnacademy.ai_analysis_result_service.common.exception.base.CommonHttpException;
import com.nhnacademy.ai_analysis_result_service.common.exception.base.JsonProcessingFailedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandleAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception e) {
        log.warn("입력값 유효성 검증 실패: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("입력값이 잘못되었습니다."));
    }

    @ExceptionHandler(JsonProcessingFailedException.class)
    public ResponseEntity<ErrorResponse> handleJsonProcessingFailedException(JsonProcessingFailedException e) {
        log.warn("JsonProcessingFailedException 발생: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse("요청 데이터를 처리할 수 없습니다."));
    }

    @ExceptionHandler(CommonHttpException.class)
    public ResponseEntity<ErrorResponse> handleCommonHttpException(CommonHttpException e) {
        log.warn("CommonHttpException 발생: {}", e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Throwable e) {
        log.error("Unhandled Exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("서버 내부 오류가 발생했습니다."));
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private String message;
    }
}
