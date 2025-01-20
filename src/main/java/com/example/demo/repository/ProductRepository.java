package com.example.demo.repository;

import com.example.demo.dto.product.ProductOrderStatsDto;
import com.example.demo.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p LEFT JOIN p.categories c WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:description IS NULL OR p.description LIKE %:description%) AND " +
            "(:category IS NULL OR c.name = :category) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:minQuantity IS NULL OR p.quantity >= :minQuantity) AND " +
            "(:maxQuantity IS NULL OR p.quantity <= :maxQuantity) AND " +
            "(:available IS NULL OR p.available = :available) " +
            "GROUP BY p.id")
    Page<Product> searchProducts(@Param("name") String name,
                                 @Param("description") String description,
                                 @Param("category") String category,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice,
                                 @Param("minQuantity") Integer minQuantity,
                                 @Param("maxQuantity") Integer maxQuantity,
                                 @Param("available") Boolean available,
                                 Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.categories WHERE p.id = :productId")
    Optional<Product> findProductById(@Param("productId") UUID productId);

    @Query(value = "SELECT p.name as productName, c.name as categoryName, u.username as username, " +
            "COUNT(oe.id) as orderCount, " +
            "ROUND(COALESCE(SUM(oe.quantity * p.price), 0), 2) as totalOrderValue, " +
            "ROUND(COALESCE(AVG(p.price), 0), 2) as averageProductPrice, " +
            "ROUND(COALESCE(AVG(oe.quantity * p.price), 0), 2) as averageOrderPrice " +
            "FROM PRODUCT p " +
            "JOIN PRODUCT_CATEGORIES pc ON p.id = pc.products_id " +
            "JOIN CATEGORY c ON pc.categories_id = c.id " +
            "JOIN ORDER_ELEMENT oe ON p.id = oe.product_id " +
            "JOIN ORDERS o ON oe.order_id = o.id " +
            "JOIN USER u ON o.user_id = u.id " +
            "WHERE (:categoryName IS NULL OR c.name = :categoryName) AND " +
            "(:startDate IS NULL OR o.created_at >= :startDate) AND " +
            "(:endDate IS NULL OR o.created_at <= :endDate) " +
            "GROUP BY p.id, p.name, c.name, u.username " +
            "ORDER BY SUM(oe.quantity * p.price) DESC", nativeQuery = true)
    List<ProductOrderStatsDto> findProductOrderStatsByCategoryAndCreatedWithinDateRange(@Param("categoryName") String categoryName,
                                                                                        @Param("startDate") LocalDateTime startDate,
                                                                                        @Param("endDate") LocalDateTime endDate);
}
