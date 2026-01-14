package com.soundbar91.user.api.dto.response;

import com.soundbar91.user.domain.entity.User;
import com.soundbar91.user.domain.vo.UserRole;

import java.time.LocalDateTime;

/**
 * 사용자 응답 DTO
 */
public record UserResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        UserRole role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
