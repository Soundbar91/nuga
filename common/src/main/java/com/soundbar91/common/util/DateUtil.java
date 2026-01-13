package com.soundbar91.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 날짜/시간 처리 유틸리티
 */
public final class DateUtil {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATETIME_COMPACT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private DateUtil() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    /**
     * LocalDate를 문자열로 변환 (yyyy-MM-dd)
     */
    public static String formatDate(LocalDate date) {
        return date == null ? null : date.format(DATE_FORMATTER);
    }

    /**
     * LocalDateTime을 문자열로 변환 (yyyy-MM-dd HH:mm:ss)
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(DATETIME_FORMATTER);
    }

    /**
     * LocalDateTime을 압축 형식으로 변환 (yyyyMMddHHmmss)
     */
    public static String formatDateTimeCompact(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(DATETIME_COMPACT_FORMATTER);
    }

    /**
     * 문자열을 LocalDate로 변환 (yyyy-MM-dd)
     */
    public static LocalDate parseDate(String dateStr) {
        if (StringUtil.isNullOrEmpty(dateStr)) {
            return null;
        }
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * 문자열을 LocalDateTime으로 변환 (yyyy-MM-dd HH:mm:ss)
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (StringUtil.isNullOrEmpty(dateTimeStr)) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    /**
     * 두 날짜 사이의 일수 계산
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 현재 날짜가 특정 날짜 이후인지 확인
     */
    public static boolean isAfterToday(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }

    /**
     * 현재 날짜가 특정 날짜 이전인지 확인
     */
    public static boolean isBeforeToday(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }

    /**
     * 오늘인지 확인
     */
    public static boolean isToday(LocalDate date) {
        return date != null && date.isEqual(LocalDate.now());
    }
}
