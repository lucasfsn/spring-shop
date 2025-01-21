package com.example.demo.dto.order;

import com.example.demo.model.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderWithUserIdAndTotalPriceDto {
    private Order order;
    private UUID userId;
    private double totalPrice;
}
