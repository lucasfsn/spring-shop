package com.example.demo.dto.order;

import com.example.demo.dto.product.ProductResDto;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderElementResDto {
    private ProductResDto product;
    private int quantity;
    private double price;
}
