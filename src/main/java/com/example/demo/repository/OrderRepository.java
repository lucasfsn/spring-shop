package com.example.demo.repository;

import com.example.demo.dto.order.OrderStatsDto;
import com.example.demo.dto.order.OrderWithTotalPriceDto;
import com.example.demo.dto.order.OrderWithUserIdAndTotalPriceDto;
import com.example.demo.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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

    @Query("SELECT new com.example.demo.dto.order.OrderWithTotalPriceDto(o, " +
            "COALESCE(SUM(oe.quantity * p.price), 0)) " +
            "FROM Order o " +
            "JOIN o.orderElements oe " +
            "JOIN oe.product p " +
            "WHERE o.user.username = :username " +
            "GROUP BY o.id")
    List<OrderWithTotalPriceDto> findOrdersByUsername(@Param("username") String username);

    @Query("SELECT new com.example.demo.dto.order.OrderWithUserIdAndTotalPriceDto(o, o.user.id, " +
            "COALESCE(SUM(oe.quantity * p.price), 0)) " +
            "FROM Order o " +
            "JOIN o.orderElements oe " +
            "JOIN oe.product p " +
            "GROUP BY o.user.id, o.id")
    List<OrderWithUserIdAndTotalPriceDto> findAllUsersOrders();

    @Query(value = "SELECT c.name as categoryName, " +
            "COUNT(DISTINCT o.id) as ordersCount, " +
            "ROUND(COALESCE(SUM(oe.quantity * p.price), 0), 2) as totalOrdersValue, " +
            "ROUND(COALESCE(AVG(p.price), 0), 2) as averageProductPrice, " +
            "ROUND(COALESCE(SUM(oe.quantity * p.price) / COUNT(DISTINCT o.id), 0), 2) as averageOrderPrice, " +
            "SUM(oe.quantity) as totalQuantityOrdered " +
            "FROM CATEGORY c " +
            "JOIN PRODUCT_CATEGORIES pc ON c.id = pc.categories_id " +
            "JOIN PRODUCT p ON pc.products_id = p.id " +
            "JOIN ORDER_ELEMENT oe ON p.id = oe.product_id " +
            "JOIN ORDERS o ON oe.order_id = o.id " +
            "WHERE (:categoryName IS NULL OR c.name = :categoryName) AND " +
            "(:startDate IS NULL OR o.created_at >= :startDate) AND " +
            "(:endDate IS NULL OR o.created_at <= :endDate) " +
            "GROUP BY c.name " +
            "ORDER BY SUM(oe.quantity * p.price) DESC", nativeQuery = true)
    List<OrderStatsDto> findOrderStatsByCategoryAndCreatedWithinDateRange(@Param("categoryName") String categoryName,
                                                                          @Param("startDate") LocalDateTime startDate,
                                                                          @Param("endDate") LocalDateTime endDate);
}
