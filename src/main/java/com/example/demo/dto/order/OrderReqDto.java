package com.example.demo.dto.order;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderReqDto {
    private UUID id;
    private UUID userId;
    private DeliveryInfoDto deliveryInfo;
    private List<OrderElementReqDto> orderElements;
}
