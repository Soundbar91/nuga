package com.soundbar91.domain.user.repository;

import com.soundbar91.domain.user.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * 사용자 Repository 인터페이스
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void delete(User user);

    boolean existsByEmail(String email);
}
