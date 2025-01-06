package com.example.demo.repository;

import com.example.demo.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
