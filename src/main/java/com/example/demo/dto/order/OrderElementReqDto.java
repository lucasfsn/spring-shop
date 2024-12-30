package com.example.demo.dto.order;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderElementReqDto {
    private UUID productId;
    private int quantity;
    private double price;
}
