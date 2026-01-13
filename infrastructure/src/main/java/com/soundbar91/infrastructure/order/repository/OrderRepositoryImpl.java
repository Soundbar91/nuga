package com.soundbar91.infrastructure.order.repository;

import com.soundbar91.domain.order.entity.Order;
import com.soundbar91.domain.order.repository.OrderRepository;
import com.soundbar91.domain.order.vo.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Order Repository 구현체
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    public OrderRepositoryImpl(OrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        return jpaRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId);
    }

    @Override
    public List<Order> findByShopId(Long shopId) {
        return jpaRepository.findByShopId(shopId);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return jpaRepository.findByStatus(status);
    }

    @Override
    public void delete(Order order) {
        jpaRepository.delete(order);
    }
}
