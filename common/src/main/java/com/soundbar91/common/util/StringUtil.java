package com.soundbar91.common.util;

/**
 * 문자열 처리 유틸리티
 */
public final class StringUtil {

    private StringUtil() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    /**
     * 문자열이 null이거나 빈 문자열인지 확인
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 문자열이 null이 아니고 비어있지 않은지 확인
     */
    public static boolean hasText(String str) {
        return !isNullOrEmpty(str);
    }

    /**
     * 이메일 마스킹 (예: test@example.com -> t**t@example.com)
     */
    public static String maskEmail(String email) {
        if (isNullOrEmpty(email) || !email.contains("@")) {
            return email;
        }

        String[] parts = email.split("@");
        String localPart = parts[0];

        if (localPart.length() <= 2) {
            return "*".repeat(localPart.length()) + "@" + parts[1];
        }

        String masked = localPart.charAt(0) +
                        "*".repeat(localPart.length() - 2) +
                        localPart.charAt(localPart.length() - 1);

        return masked + "@" + parts[1];
    }

    /**
     * 전화번호 마스킹 (예: 010-1234-5678 -> 010-****-5678)
     */
    public static String maskPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) {
            return phoneNumber;
        }

        String digits = phoneNumber.replaceAll("[^0-9]", "");

        if (digits.length() != 11) {
            return phoneNumber;
        }

        return digits.substring(0, 3) + "-****-" + digits.substring(7);
    }

    /**
     * 문자열 앞뒤 공백 제거 (null-safe)
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 기본값을 가진 trim (null이면 기본값 반환)
     */
    public static String trimOrDefault(String str, String defaultValue) {
        String trimmed = trim(str);
        return isNullOrEmpty(trimmed) ? defaultValue : trimmed;
    }
}
