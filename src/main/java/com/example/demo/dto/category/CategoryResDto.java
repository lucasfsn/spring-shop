package com.example.demo.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CategoryResDto {
    private UUID id;
    private String name;
}
