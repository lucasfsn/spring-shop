package com.example.demo.dto.order;

import com.example.demo.model.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderResDto {
    private UUID id;
    private LocalDateTime createdAt;
    private DeliveryInfoDto deliveryInfo;
    private List<OrderProductDto> products;
    private double totalPrice;
    private OrderStatus status;
}
