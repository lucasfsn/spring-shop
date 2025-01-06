package com.example.demo.dto.order;

import com.example.demo.model.order.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ChangeOrderStatusDto {
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
