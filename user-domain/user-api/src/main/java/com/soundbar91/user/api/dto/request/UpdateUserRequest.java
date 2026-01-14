package com.soundbar91.user.api.dto.request;

/**
 * 사용자 수정 요청 DTO
 */
public record UpdateUserRequest(
        String name,
        String phoneNumber
) {
}
