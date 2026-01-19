package com.soundbar91.security.exception;

public enum AuthErrorCode {

    // 인증 관련
    INVALID_TOKEN("AUTH001", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("AUTH002", "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN("AUTH003", "지원하지 않는 토큰 형식입니다."),
    EMPTY_TOKEN("AUTH004", "토큰이 비어있습니다."),

    // 인가 관련
    ACCESS_DENIED("AUTH005", "접근 권한이 없습니다."),
    INSUFFICIENT_AUTHORITY("AUTH006", "권한이 부족합니다."),

    // 로그인 관련
    INVALID_CREDENTIALS("AUTH007", "아이디 또는 비밀번호가 올바르지 않습니다."),
    ACCOUNT_DISABLED("AUTH008", "비활성화된 계정입니다."),
    ACCOUNT_LOCKED("AUTH009", "잠긴 계정입니다."),
    ACCOUNT_EXPIRED("AUTH010", "만료된 계정입니다."),

    // 기타
    AUTHENTICATION_FAILED("AUTH011", "인증에 실패했습니다.");

    private final String code;
    private final String message;

    AuthErrorCode(String code, String message) {
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
