package org.sopt.global.code;

public enum CommonErrorCode implements BaseErrorCode {
    INTERNAL_SERVER_ERROR("COMMON500", "서버 오류가 발생했습니다.");

    private final String code;
    private final String message;

    CommonErrorCode(String code, String message) {
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
