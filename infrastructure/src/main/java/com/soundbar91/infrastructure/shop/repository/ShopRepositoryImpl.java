package com.soundbar91.infrastructure.shop.repository;

import com.soundbar91.domain.shop.entity.Shop;
import com.soundbar91.domain.shop.repository.ShopRepository;
import com.soundbar91.domain.shop.vo.ShopCategory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Shop Repository 구현체
 */
@Repository
public class ShopRepositoryImpl implements ShopRepository {

    private final ShopJpaRepository jpaRepository;

    public ShopRepositoryImpl(ShopJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Shop save(Shop shop) {
        return jpaRepository.save(shop);
    }

    @Override
    public Optional<Shop> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Shop> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Shop> findByOwnerId(Long ownerId) {
        return jpaRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Shop> findByCategory(ShopCategory category) {
        return jpaRepository.findByCategory(category);
    }

    @Override
    public List<Shop> findByIsActive(Boolean isActive) {
        return jpaRepository.findByIsActive(isActive);
    }

    @Override
    public void delete(Shop shop) {
        jpaRepository.delete(shop);
    }
}
