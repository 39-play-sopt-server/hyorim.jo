package org.sopt.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.global.code.BaseErrorCode;
import org.sopt.global.code.CommonErrorCode;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 서비스에선 GeneralException로 예외를 던져야 한다.
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(GeneralException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        log.warn("[서비스 예외] code={}, message={}", errorCode.getCode(), errorCode.getMessage());
        return toResponse(errorCode);
    }

    // TODO: 에러 종류 별 예외 처리
    // 스프링 예외를 ApiResponse으로 변경
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (statusCode.is5xxServerError()) {
            log.error("[스프링 MVC 예외] status={}", statusCode.value(), e);
        } else {
            log.warn("[스프링 MVC 예외] status={}, message={}", statusCode.value(), e.getMessage());
        }

        ApiResponse<Void> response = ApiResponse.fail("COMMON" + statusCode.value(), e.getMessage());
        return ResponseEntity.status(statusCode).headers(headers).body(response);
    }

    // 예상 못 한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("[예상 못 한 예외] ", e);
        return toResponse(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiResponse<Void>> toResponse(BaseErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.fail(errorCode));
    }
}