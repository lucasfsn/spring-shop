package com.example.demo.mapper.order;

import com.example.demo.dto.order.DeliveryInfoDto;
import com.example.demo.dto.order.OrderResDto;
import com.example.demo.model.order.Order;
import com.example.demo.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final DeliveryInfoMapper deliveryInfoMapper;
    private final OrderElementMapper orderElementMapper;

    public OrderResDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderResDto dto = new OrderResDto();
        dto.setId(order.getId());
        dto.setDeliveryInfo(deliveryInfoMapper.toDto(order.getDeliveryInfo()));
        dto.setProducts(order.getOrderElements().stream()
                .map(orderElementMapper::toDto)
                .toList());
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }

    public Order toEntity(DeliveryInfoDto deliveryInfoDto, User user) {
        if (deliveryInfoDto == null || user == null) {
            return null;
        }

        Order order = new Order();
        order.setUser(user);
        order.setDeliveryInfo(deliveryInfoMapper.toEntity(deliveryInfoDto));
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }
}
