package com.soundbar91.domain.order.service;

import com.soundbar91.common.exception.NotFoundException;
import com.soundbar91.domain.order.entity.Order;
import com.soundbar91.domain.order.repository.OrderRepository;
import com.soundbar91.domain.order.vo.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 주문 서비스
 */
@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * 주문 생성
     */
    @Transactional
    public Order createOrder(Long userId, Long shopId, String productName, Integer quantity, BigDecimal totalAmount, String deliveryAddress) {
        Order order = new Order(userId, shopId, productName, quantity, totalAmount, deliveryAddress);
        return orderRepository.save(order);
    }

    /**
     * 주문 조회
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다. ID: " + id));
    }

    /**
     * 모든 주문 조회
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * 사용자별 주문 조회
     */
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    /**
     * 상점별 주문 조회
     */
    public List<Order> getOrdersByShopId(Long shopId) {
        return orderRepository.findByShopId(shopId);
    }

    /**
     * 상태별 주문 조회
     */
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    /**
     * 주문 확정
     */
    @Transactional
    public Order confirmOrder(Long id) {
        Order order = getOrderById(id);
        order.confirm();
        return order;
    }

    /**
     * 주문 배송 시작
     */
    @Transactional
    public Order shipOrder(Long id) {
        Order order = getOrderById(id);
        order.ship();
        return order;
    }

    /**
     * 주문 배송 완료
     */
    @Transactional
    public Order deliverOrder(Long id) {
        Order order = getOrderById(id);
        order.deliver();
        return order;
    }

    /**
     * 주문 취소
     */
    @Transactional
    public Order cancelOrder(Long id) {
        Order order = getOrderById(id);
        order.cancel();
        return order;
    }

    /**
     * 주문 삭제
     */
    @Transactional
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}
