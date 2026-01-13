package com.soundbar91.infrastructure.order.repository;

import com.soundbar91.domain.order.entity.Order;
import com.soundbar91.domain.order.vo.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Order Spring Data JPA Repository
 */
public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findByShopId(Long shopId);

    List<Order> findByStatus(OrderStatus status);
}
