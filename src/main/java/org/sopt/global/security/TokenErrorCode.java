package org.sopt.global.security;

import org.sopt.global.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum TokenErrorCode implements BaseErrorCode {
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "TOKEN4011", "인증 토큰이 필요합니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "TOKEN4012", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN4013", "만료된 토큰입니다."),
    TOKEN_REFRESH_INVALID(HttpStatus.UNAUTHORIZED, "TOKEN4014", "유효하지 않은 리프레시 토큰입니다. 다시 로그인해 주세요."),
    TOKEN_REFRESH_MISSING(HttpStatus.UNAUTHORIZED, "TOKEN4015", "리프레시 토큰이 없습니다. 다시 로그인해 주세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    TokenErrorCode(HttpStatus httpStatus, String code, String message) {
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
