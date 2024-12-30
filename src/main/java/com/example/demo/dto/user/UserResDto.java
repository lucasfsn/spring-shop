package com.example.demo.dto.user;

import com.example.demo.model.user.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class UserResDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private UserRole role;
}
