package com.example.demo.repository;

import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT COUNT(*) > 0 FROM user WHERE email = :email AND id != :id", nativeQuery = true)
    boolean existsByEmailAndIdIsNot(@Param("email") String email, @Param("id") UUID id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
