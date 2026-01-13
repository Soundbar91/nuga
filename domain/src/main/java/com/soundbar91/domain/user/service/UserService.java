package com.soundbar91.domain.user.service;

import com.soundbar91.common.exception.BusinessException;
import com.soundbar91.common.exception.ErrorCode;
import com.soundbar91.common.exception.NotFoundException;
import com.soundbar91.domain.user.entity.User;
import com.soundbar91.domain.user.repository.UserRepository;
import com.soundbar91.domain.user.vo.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 사용자 서비스
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자 생성
     */
    @Transactional
    public User createUser(String name, String email, String phoneNumber, UserRole role) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE, "이미 존재하는 이메일입니다: " + email);
        }

        User user = new User(name, email, phoneNumber, role);
        return userRepository.save(user);
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
}
