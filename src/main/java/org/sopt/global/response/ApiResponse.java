package org.sopt.global.response;

import org.sopt.global.code.BaseCode;
import org.sopt.global.code.BaseErrorCode;
import org.sopt.global.code.SuccessCode;
import org.springframework.http.ResponseEntity;

public record ApiResponse<T>(boolean isSuccess, String code, String message, T data) {
    // 조회
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return success(SuccessCode.OK, data);
    }

    // 수정 및 삭제
    public static <T> ResponseEntity<ApiResponse<T>> ok() {
        return ok(null);
    }

    // 생성
    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return success(SuccessCode.CREATED, data);
    }

    // 실패
    public static <T> ApiResponse<T> fail(BaseErrorCode errorCode) {
        return of(errorCode, null);
    }

    // 스프링 예외의 상태 코드를 가져다 쓰는데 필요
    public static <T> ApiResponse<T> fail(String errorCode, String message) {
        return new ApiResponse<>(false, errorCode, message, null);
    }

    private static <T> ResponseEntity<ApiResponse<T>> success(SuccessCode successCode, T data) {
        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(of(successCode, data));
    }

    private static <T> ApiResponse<T> of(BaseCode code, T data) {
        boolean isSuccess = !(code instanceof BaseErrorCode);
        return new ApiResponse<>(isSuccess, code.getCode(), code.getMessage(), data);
    }
}
