package com.example.demo.dto.order;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderProductDto {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private int quantity;
}
