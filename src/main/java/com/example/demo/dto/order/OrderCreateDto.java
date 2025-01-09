package com.example.demo.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderCreateDto {
    private UUID id;
}
