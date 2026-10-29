package org.sopt.global.code;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements BaseErrorCode {
    INVALID_PAGE_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4001", "page는 0 이상, size는 1 이상이어야 합니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CommonErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
