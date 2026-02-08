package com.example.Bookstore.order.domain;

import com.example.Bookstore.order.domain.models.OrderStatus;
import com.example.Bookstore.order.domain.models.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus status);

    default void updateOrderStatus(String orderNumber, OrderStatus orderStatus){
        OrderEntity order = findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(orderStatus);
        save(order);
    }

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    @Query("""
            select new com.example.Bookstore.order.domain.models.OrderSummary(o.orderNumber, o.status)
            from OrderEntity o
            where o.username = :username
            """)
    List<OrderSummary> findByUsername(String username);

    @Query("""
            select distinct o
            from OrderEntity o left join fetch o.items
            where o.username = :username and o.orderNumber = :orderNumber
            """)
    Optional<OrderEntity> findByUsernameAndOrderNumber(String username, String orderNumber);
}
