package org.sopt.community.code;

import org.sopt.global.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum CommunityErrorCode implements BaseErrorCode {
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMUNITY4041", "존재하지 않는 게시판입니다."),
    PREFIX_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMUNITY4042", "존재하지 않는 말머리입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CommunityErrorCode(HttpStatus httpStatus, String code, String message) {
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
