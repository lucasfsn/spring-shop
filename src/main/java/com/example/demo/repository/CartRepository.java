package com.example.demo.repository;

import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query("SELECT c FROM Cart c JOIN c.user u WHERE u.username = :username")
    Optional<Cart> findByUsername(@Param("username") String username);

    @Query("SELECT ce FROM CartElement ce WHERE ce.product.id = :productId AND ce.cart.id = :cartId")
    Optional<CartElement> findCartElementByProductIdAndCartId(@Param("productId") UUID productId, @Param("cartId") UUID cartId);
}
