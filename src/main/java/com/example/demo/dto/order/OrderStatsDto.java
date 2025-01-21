package com.example.demo.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderStatsDto {
    private String categoryName;
    private Long ordersCount;
    private Double totalOrdersValue;
    private Double averageProductPrice;
    private Double averageOrderPrice;
    private Long totalQuantityOrdered;
}