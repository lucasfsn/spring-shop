package com.example.demo.dto.order;

import com.example.demo.model.order.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeOrderStatusDto {
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
