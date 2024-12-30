package com.example.demo.dto.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResDto {
    private UUID id;
    private UUID userId;
    private LocalDateTime orderDate;
    private DeliveryInfoDto deliveryInfo;
    private double totalPrice;
    private List<OrderElementResDto> orderElements;
}
