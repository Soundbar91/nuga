package com.soundbar91.user.domain.repository;

import com.soundbar91.user.domain.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * 사용자 레포지토리 인터페이스
 * 도메인 계층에서 정의하고, infrastructure 계층에서 구현
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    boolean existsByEmail(String email);

    void delete(User user);

    void deleteById(Long id);
}
