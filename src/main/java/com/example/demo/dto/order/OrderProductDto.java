package com.example.demo.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderProductDto {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private int quantity;
}
