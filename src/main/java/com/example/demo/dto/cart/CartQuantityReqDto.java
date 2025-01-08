package com.example.demo.dto.cart;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartQuantityReqDto {
    @Positive(message = "Quantity must be positive")
    private int quantity;
}
