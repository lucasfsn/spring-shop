package com.example.demo.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderCreateDto {
    private UUID id;
}
