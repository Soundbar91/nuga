package com.soundbar91.domain.shop.repository;

import com.soundbar91.domain.shop.entity.Shop;
import com.soundbar91.domain.shop.vo.ShopCategory;
import java.util.List;
import java.util.Optional;

/**
 * 상점 Repository 인터페이스
 */
public interface ShopRepository {

    Shop save(Shop shop);

    Optional<Shop> findById(Long id);

    List<Shop> findAll();

    List<Shop> findByOwnerId(Long ownerId);

    List<Shop> findByCategory(ShopCategory category);

    List<Shop> findByIsActive(Boolean isActive);

    void delete(Shop shop);
}
