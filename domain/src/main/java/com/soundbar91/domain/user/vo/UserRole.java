package com.soundbar91.domain.user.vo;

/**
 * 사용자 역할
 */
public enum UserRole {
    USER("일반 사용자"),
    ADMIN("관리자"),
    MANAGER("매니저");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
