package com.example.demo.repository;

import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    //    @Query("SELECT c FROM Cart c JOIN c.user u WHERE u.username = :username")
    //    Optional<Cart> findByUsername(@Param("username") String username);
    @Query("SELECT c FROM Cart c " +
            "JOIN c.user u " +
            "LEFT JOIN FETCH c.cartElements ce " +
            "LEFT JOIN FETCH ce.product " +
            "WHERE u.username = :username")
    Optional<Cart> findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM cart_element el WHERE el.product_id = :productId AND el.cart_id = :cartId", nativeQuery = true)
    Optional<CartElement> findCartElementByProductIdAndCartId(@Param("productId") UUID productId, @Param("cartId") UUID cartId);
}
