package com.example.demo.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResDto {
    private String token;
    private UserResDto user;
}
