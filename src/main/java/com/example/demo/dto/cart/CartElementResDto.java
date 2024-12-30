package com.example.demo.dto.cart;

import com.example.demo.dto.product.ProductResDto;
import lombok.Data;

import java.util.UUID;

@Data
public class CartElementResDto {
    private UUID id;
    private ProductResDto productResDto;
    private int quantity;
}
