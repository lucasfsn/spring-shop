package com.example.demo.dto.cart;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;
    private List<CartElementResDto> elements;
}
