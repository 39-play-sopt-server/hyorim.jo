package org.sopt.member.code;

import org.sopt.global.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements BaseErrorCode {
    PROFILE_NOT_OWNED(HttpStatus.FORBIDDEN, "MEMBER4031", "프로필 권한이 없습니다."),

    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4041", "존재하지 않는 프로필입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4042", "존재하지 않는 회원입니다."),

    MEMBER_HAS_PROFILES(HttpStatus.CONFLICT, "MEMBER4092", "프로필이 있는 회원은 삭제할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String code, String message) {
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
