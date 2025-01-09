package com.example.demo.repository;

import com.example.demo.dto.order.OrderWithTotalPriceDto;
import com.example.demo.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT new com.example.demo.dto.order.OrderWithTotalPriceDto(o, " +
            "COALESCE(SUM(oe.quantity * p.price), 0)) " +
            "FROM Order o " +
            "JOIN o.orderElements oe " +
            "JOIN oe.product p " +
            "WHERE o.id = :orderId " +
            "GROUP BY o.id")
    Optional<OrderWithTotalPriceDto> findOrderWithTotalPrice(@Param("orderId") UUID orderId);

    @Query("SELECT o, COALESCE(SUM(oe.quantity * p.price), 0) as totalPrice " +
            "FROM Order o " +
            "JOIN o.orderElements oe " +
            "JOIN oe.product p " +
            "WHERE o.user.username = :username " +
            "GROUP BY o.id")
    List<Object[]> findOrdersByUsername(@Param("username") String username);

    @Query("SELECT o.user.id, o, COALESCE(SUM(oe.quantity * p.price), 0) as totalPrice " +
            "FROM Order o " +
            "JOIN o.orderElements oe " +
            "JOIN oe.product p " +
            "GROUP BY o.user.id, o.id")
    List<Object[]> findAllUsersOrders();
}
