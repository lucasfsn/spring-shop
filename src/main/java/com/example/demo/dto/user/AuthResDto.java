package com.example.demo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResDto {
    private String token;
    private UserResDto user;
}
