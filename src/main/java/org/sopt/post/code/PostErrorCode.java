package org.sopt.post.code;

import org.sopt.global.code.BaseErrorCode;

public enum PostErrorCode implements BaseErrorCode {
    POST_TITLE_EMPTY("POST4001", "제목을 입력해 주세요."),
    POST_CONTENT_EMPTY("POST4002", "내용을 입력해 주세요."),

    POST_NOT_FOUND("POST4041", "존재하지 않는 게시글입니다.");

    private final String code;
    private final String message;

    PostErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
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
