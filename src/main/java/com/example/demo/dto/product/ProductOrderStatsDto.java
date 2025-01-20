package com.example.demo.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductOrderStatsDto {
    private String productName;
    private String categoryName;
    private String username;
    private Long orderCount;
    private Double totalOrderValue;
    private Double averageProductPrice;
    private Double averageOrderPrice;
}