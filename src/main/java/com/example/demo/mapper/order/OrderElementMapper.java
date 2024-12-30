package com.example.demo.mapper.order;

import com.example.demo.dto.order.OrderElementReqDto;
import com.example.demo.dto.order.OrderElementResDto;
import com.example.demo.mapper.product.ProductMapper;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderElement;
import com.example.demo.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderElementMapper {

    private final ProductMapper productMapper;

    public OrderElement toEntity(OrderElementReqDto dto, Order order, Product product) {
        if (dto == null || product == null || order == null) {
            return null;
        }

        OrderElement orderElement = new OrderElement();
        orderElement.setProduct(product);
        orderElement.setOrder(order);
        orderElement.setQuantity(dto.getQuantity());
        orderElement.setPrice(dto.getPrice());
        return orderElement;
    }

    public OrderElementResDto toDto(OrderElement entity) {
        if (entity == null) {
            return null;
        }

        OrderElementResDto dto = new OrderElementResDto();
        dto.setProduct(productMapper.toDto(entity.getProduct()));
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}