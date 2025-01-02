package com.example.demo.mapper.order;

import com.example.demo.dto.order.OrderProductDto;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderElement;
import com.example.demo.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderElementMapper {
    public OrderElement toEntity(Order order, Product product, int quantity) {
        if (product == null || order == null) {
            return null;
        }

        OrderElement orderElement = new OrderElement();
        orderElement.setProduct(product);
        orderElement.setOrder(order);
        orderElement.setQuantity(quantity);
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