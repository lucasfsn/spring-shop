package com.example.demo.dto.product;

import com.example.demo.dto.category.CategoryResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductResDto {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private boolean available;
    private int quantity;
    private List<CategoryResDto> categories;
}
