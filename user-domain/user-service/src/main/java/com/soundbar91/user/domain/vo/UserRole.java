package com.soundbar91.user.domain.vo;

/**
 * 사용자 역할
 */
public enum UserRole {
    ADMIN("관리자"),
    USER("일반 사용자"),
    SELLER("판매자");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
