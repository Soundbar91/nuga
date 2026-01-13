package com.soundbar91.domain.shop.vo;

/**
 * 상점 카테고리
 */
public enum ShopCategory {
    FOOD("음식점"),
    CAFE("카페"),
    RETAIL("소매점"),
    SERVICE("서비스"),
    ENTERTAINMENT("엔터테인먼트");

    private final String description;

    ShopCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
