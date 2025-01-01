package com.example.demo.dto.cart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartQuantityReqDto {
    @Positive(message = "Quantity must be positive")
    private int quantity;
}
