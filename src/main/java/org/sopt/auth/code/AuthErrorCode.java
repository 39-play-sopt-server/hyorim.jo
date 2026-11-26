package org.sopt.auth.code;

import org.sopt.global.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements BaseErrorCode {
    AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH4011", "이메일 또는 비밀번호가 올바르지 않습니다."),
    AUTH_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "AUTH4012", "인증 토큰이 필요합니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4013", "유효하지 않은 토큰입니다."),
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4014", "만료된 토큰입니다."),

    AUTH_ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH4031", "접근 권한이 없습니다."),

    AUTH_DUPLICATE_EMAIL(HttpStatus.CONFLICT, "AUTH4091", "이미 가입된 이메일입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String code, String message) {
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
