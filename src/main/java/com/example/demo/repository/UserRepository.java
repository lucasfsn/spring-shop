package com.example.demo.repository;

import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsernameAndIdIsNot(String username, UUID id);
    boolean existsByEmailAndIdIsNot(String email, UUID id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
