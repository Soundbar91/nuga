package com.soundbar91.common.constant;

/**
 * 애플리케이션 공통 상수
 */
public final class AppConstants {

    private AppConstants() {
        throw new AssertionError("Constant class cannot be instantiated");
    }

    /**
     * 페이징 관련 상수
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final int MIN_PAGE_SIZE = 1;
    public static final int DEFAULT_PAGE_NUMBER = 0;

    /**
     * 날짜 형식 상수
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_COMPACT_FORMAT = "yyyyMMddHHmmss";

    /**
     * 문자열 길이 제한
     */
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_DESCRIPTION_LENGTH = 500;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MAX_PHONE_LENGTH = 20;

    /**
     * HTTP 헤더
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ACCEPT = "Accept";

    /**
     * 기본값
     */
    public static final String DEFAULT_LOCALE = "ko_KR";
    public static final String DEFAULT_TIMEZONE = "Asia/Seoul";
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 정규식 패턴
     */
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PHONE_PATTERN = "^01[0-9]-?[0-9]{3,4}-?[0-9]{4}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
}
