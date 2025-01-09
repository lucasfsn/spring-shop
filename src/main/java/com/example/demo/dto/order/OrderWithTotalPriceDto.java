package com.example.demo.dto.order;

import com.example.demo.model.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderWithTotalPriceDto {
    private Order order;
    private double totalPrice;
}

