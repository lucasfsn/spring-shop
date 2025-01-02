package com.example.demo.mapper.order;

import com.example.demo.dto.order.OrderResDto;
import com.example.demo.model.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
