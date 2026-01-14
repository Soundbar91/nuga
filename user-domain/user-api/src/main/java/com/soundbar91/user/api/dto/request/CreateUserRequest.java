package com.soundbar91.user.api.dto.request;

import com.soundbar91.user.domain.vo.UserRole;

/**
 * 사용자 생성 요청 DTO
 */
public record CreateUserRequest(
        String name,
        String email,
        String phoneNumber,
        UserRole role
) {
}
