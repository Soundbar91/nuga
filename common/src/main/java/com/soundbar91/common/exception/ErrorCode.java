package com.soundbar91.common.exception;

/**
 * 공통 에러 코드 정의
 */
public enum ErrorCode {

    // 공통 에러
    INVALID_INPUT("E001", "잘못된 입력입니다."),
    NOT_FOUND("E002", "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("E003", "서버 내부 오류가 발생했습니다."),

    // 인증/인가 에러
    UNAUTHORIZED("E101", "인증이 필요합니다."),
    FORBIDDEN("E102", "접근 권한이 없습니다."),

    // 비즈니스 로직 에러
    DUPLICATE_RESOURCE("E201", "중복된 리소스가 존재합니다."),
    INVALID_STATE("E202", "유효하지 않은 상태입니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
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
