package com.example.demo;

import com.example.demo.model.user.User;
import com.example.demo.model.user.UserRole;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjektApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjektApplication.class, args);
    }

    @Bean
    CommandLineRunner appSetup(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.existsByUsername("admin-user")) {
                return;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("User");
            user.setUsername("admin-user");
            user.setEmail("admin@mail.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(UserRole.ADMIN);
            userRepository.save(user);
        };
    }
}
