package com.soundbar91.domain.order.repository;

import com.soundbar91.domain.order.entity.Order;
import com.soundbar91.domain.order.vo.OrderStatus;
import java.util.List;
import java.util.Optional;

/**
 * 주문 Repository 인터페이스
 */
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    List<Order> findByUserId(Long userId);

    List<Order> findByShopId(Long shopId);

    List<Order> findByStatus(OrderStatus status);

    void delete(Order order);
}
