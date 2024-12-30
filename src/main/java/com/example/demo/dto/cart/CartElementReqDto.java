package com.example.demo.dto.cart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class CartElementReqDto {
    private UUID id;
    private UUID productId;
    private UUID cartId;
    @Positive(message = "Quantity must be positive")
    private int quantity;
}
