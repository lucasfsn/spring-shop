package com.example.demo.dto.user;

import com.example.demo.model.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserResDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private UserRole role;

    public boolean hasAdminRole() {
        return UserRole.ADMIN.equals(role);
    }
}
