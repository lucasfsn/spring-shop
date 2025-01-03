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

        OrderElement orderElement = new OrderElement();
        orderElement.setProduct(cartElement.getProduct());
        orderElement.setOrder(order);
        orderElement.setQuantity(cartElement.getQuantity());
        return orderElement;
    }

    public OrderProductDto toDto(OrderElement entity) {
        if (entity == null) {
            return null;
        }

        OrderProductDto dto = new OrderProductDto();
        dto.setId(entity.getProduct().getId());
        dto.setName(entity.getProduct().getName());
        dto.setDescription(entity.getProduct().getDescription());
        dto.setPrice(entity.getProduct().getPrice());
        dto.setQuantity(entity.getQuantity());
        return dto;
    }
}