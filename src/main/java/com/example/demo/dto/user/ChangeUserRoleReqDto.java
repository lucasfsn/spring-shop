package com.example.demo.dto.user;

import com.example.demo.model.user.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ChangeUserRoleReqDto {
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
