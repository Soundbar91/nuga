package com.soundbar91.user.service;

import com.soundbar91.common.exception.BusinessException;
import com.soundbar91.common.exception.ErrorCode;
import com.soundbar91.common.exception.NotFoundException;
import com.soundbar91.user.domain.entity.User;
import com.soundbar91.user.domain.event.UserCreatedEvent;
import com.soundbar91.user.domain.repository.UserRepository;
import com.soundbar91.user.domain.vo.UserRole;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 사용자 서비스
 * 도메인 이벤트를 발행하여 다른 도메인과 느슨하게 결합
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserService(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * 사용자 생성
     * 생성 후 UserCreatedEvent 발행
     */
    @Transactional
    public User createUser(String name, String email, String phoneNumber, UserRole role) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE, "이미 존재하는 이메일입니다: " + email);
        }

        User user = new User(name, email, phoneNumber, role);
        User savedUser = userRepository.save(user);

        // 도메인 이벤트 발행
        eventPublisher.publishEvent(new UserCreatedEvent(savedUser.getId(), savedUser.getEmail(), savedUser.getName()));

        return savedUser;
    }

    /**
     * 사용자 조회
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다. ID: " + id));
    }

    /**
     * 이메일로 사용자 조회
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다. Email: " + email));
    }

    /**
     * 모든 사용자 조회
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 사용자 프로필 수정
     */
    @Transactional
    public User updateUserProfile(Long id, String name, String phoneNumber) {
        User user = getUserById(id);
        user.updateProfile(name, phoneNumber);
        return user;
    }

    /**
     * 사용자 역할 변경
     */
    @Transactional
    public User updateUserRole(Long id, UserRole role) {
        User user = getUserById(id);
        user.updateRole(role);
        return user;
    }

    /**
     * 사용자 삭제
     */
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    /**
     * 사용자 존재 여부 확인
     * 다른 도메인에서 사용자 검증 시 사용
     */
    public boolean existsById(Long id) {
        return userRepository.findById(id).isPresent();
    }
}
