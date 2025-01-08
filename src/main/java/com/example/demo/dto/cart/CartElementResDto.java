package com.example.demo.dto.cart;

import com.example.demo.dto.category.CategoryResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CartElementResDto {
    private UUID productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private List<CategoryResDto> productCategories;
    private int quantity;
}
