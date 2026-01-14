package com.soundbar91.user.domain.event;

import java.time.LocalDateTime;

/**
 * 사용자 생성 이벤트
 * 도메인 간 느슨한 결합을 위한 이벤트 객체
 */
public class UserCreatedEvent {

    private final Long userId;
    private final String email;
    private final String name;
    private final LocalDateTime occurredAt;

    public UserCreatedEvent(Long userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String toString() {
        return "UserCreatedEvent{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", occurredAt=" + occurredAt +
                '}';
    }
}
