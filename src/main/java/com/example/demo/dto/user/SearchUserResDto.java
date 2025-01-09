package com.example.demo.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class SearchUserResDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
}
