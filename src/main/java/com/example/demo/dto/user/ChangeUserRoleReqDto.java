package com.example.demo.dto.user;

import com.example.demo.model.user.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserRoleReqDto {
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
