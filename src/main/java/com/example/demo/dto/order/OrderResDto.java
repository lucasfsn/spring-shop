package com.example.demo.dto.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResDto {
    private UUID id;
    private LocalDateTime createdAt;
    private DeliveryInfoDto deliveryInfo;
    private List<OrderProductDto> products;
    private double totalPrice;
}
