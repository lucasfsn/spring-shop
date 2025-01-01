package com.example.demo.dto.cart;

import com.example.demo.dto.category.CategoryResDto;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CartElementResDto {
    private UUID productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private List<CategoryResDto> productCategories;
    private int quantity;
}
