package com.example.demo.mapper.order;

import com.example.demo.dto.order.DeliveryInfoDto;
import com.example.demo.dto.order.OrderResDto;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderStatus;
import com.example.demo.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final DeliveryInfoMapper deliveryInfoMapper;
    private final OrderElementMapper orderElementMapper;

    public OrderResDto toDto(Order order, Double totalPrice) {
        if (order == null) {
            return null;
        }

        return OrderResDto.builder()
                .id(order.getId())
                .deliveryInfo(deliveryInfoMapper.toDto(order.getDeliveryInfo()))
                .products(order.getOrderElements().stream()
                        .map(orderElementMapper::toDto)
                        .toList())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .totalPrice(totalPrice)
                .build();
    }

    public Order toEntity(DeliveryInfoDto deliveryInfoDto, User user) {
        if (deliveryInfoDto == null || user == null) {
            return null;
        }

        return Order.builder()
                .user(user)
                .deliveryInfo(deliveryInfoMapper.toEntity(deliveryInfoDto))
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();
    }
}
