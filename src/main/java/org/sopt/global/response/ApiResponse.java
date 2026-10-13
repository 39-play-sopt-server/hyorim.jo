package org.sopt.global.response;

import org.sopt.global.code.BaseErrorCode;
import org.sopt.global.code.SuccessCode;

public record ApiResponse<T>(boolean isSuccess, String code, String message, T data) {
    // 조회
    public static <T> ApiResponse<T> ok(T data) {
        return success(SuccessCode.OK, data);
    }

    // 수정 및 삭제
    public static <T> ApiResponse<T> ok() {
        return ok(null);
    }

    // 생성
    public static <T> ApiResponse<T> created(T data) {
        return success(SuccessCode.CREATED, data);
    }

    // 실패
    public static <T> ApiResponse<T> fail(BaseErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null);
    }

    private static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(true, successCode.getCode(), successCode.getMessage(), data);
    }
}
