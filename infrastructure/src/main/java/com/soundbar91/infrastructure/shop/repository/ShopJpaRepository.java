package com.soundbar91.infrastructure.shop.repository;

import com.soundbar91.domain.shop.entity.Shop;
import com.soundbar91.domain.shop.vo.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Shop Spring Data JPA Repository
 */
public interface ShopJpaRepository extends JpaRepository<Shop, Long> {

    List<Shop> findByOwnerId(Long ownerId);

    List<Shop> findByCategory(ShopCategory category);

    List<Shop> findByIsActive(Boolean isActive);
}
