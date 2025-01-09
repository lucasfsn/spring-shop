package com.example.demo.repository;

import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email AND u.id != :id")
    boolean existsByEmailAndIdIsNot(@Param("email") String email, @Param("id") UUID id);

    @Query("SELECT u FROM User u WHERE " +
            "(:pattern IS NULL OR LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :pattern, '%'))) OR " +
            "(:pattern IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :pattern, '%'))) OR " +
            "(:pattern IS NULL OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :pattern, '%')))")
    List<User> searchUsersByName(@Param("pattern") String pattern);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
