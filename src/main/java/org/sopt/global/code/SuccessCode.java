package org.sopt.global.code;

public enum SuccessCode {
    OK("COMMON200", "성공입니다."),
    CREATED("COMMON201", "생성했습니다.");

    private final String code;
    private final String message;

    SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
