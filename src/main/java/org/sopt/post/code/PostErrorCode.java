package org.sopt.post.code;

import org.sopt.global.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements BaseErrorCode {
    POST_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "POST4001", "제목을 입력해 주세요."),
    POST_CONTENT_EMPTY(HttpStatus.BAD_REQUEST, "POST4002", "내용을 입력해 주세요."),
    POST_AUTHOR_EMPTY(HttpStatus.BAD_REQUEST, "POST4003", "작성자를 입력해 주세요."),
    POST_INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "POST4004", "존재하지 않는 카테고리입니다."),

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST4041", "존재하지 않는 게시글입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    PostErrorCode(HttpStatus httpStatus, String code, String message) {
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
