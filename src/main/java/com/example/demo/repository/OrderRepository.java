package com.example.demo.repository;

import com.example.demo.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query(value = "SELECT * FROM orders o WHERE o.user_id = (SELECT id FROM user u WHERE u.username = :username)", nativeQuery = true)
    List<Order> findOrdersByUsername(@Param("username") String username);

    @Query("SELECT SUM(oe.quantity * p.price) as totalPrice " +
            "FROM OrderElement oe JOIN oe.product p " +
            "WHERE oe.order.id = :orderId")
    Double findTotalPriceByOrderId(@Param("orderId") UUID orderId);

    @Query("SELECT o.user.id, o FROM Order o")
    List<Object[]> findAllUsersOrders();
}
