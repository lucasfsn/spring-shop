package com.example.demo.mapper.order;

import com.example.demo.dto.order.OrderProductDto;
import com.example.demo.model.cart.CartElement;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderElementMapper {
    public OrderElement toEntity(CartElement cartElement, Order order) {
        if (cartElement == null || order == null) {
            return null;
        }

        return OrderElement.builder()
                .product(cartElement.getProduct())
                .order(order)
                .quantity(cartElement.getQuantity())
                .build();
    }

    public OrderProductDto toDto(OrderElement entity) {
        if (entity == null) {
            return null;
        }

        return OrderProductDto.builder()
                .id(entity.getProduct().getId())
                .name(entity.getProduct().getName())
                .description(entity.getProduct().getDescription())
                .price(entity.getProduct().getPrice())
                .quantity(entity.getQuantity())
                .build();
    }
}