package com.example.demo.mapper.order;

import com.example.demo.dto.order.OrderElementReqDto;
import com.example.demo.dto.order.OrderElementResDto;
import com.example.demo.dto.order.OrderReqDto;
import com.example.demo.dto.order.OrderResDto;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderElement;
import com.example.demo.model.product.Product;
import com.example.demo.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final DeliveryInfoMapper deliveryInfoMapper;
    private final OrderElementMapper orderElementMapper;

    public Order toEntity(OrderReqDto dto, Map<UUID, Product> productMap, User user) {
        if (dto == null || productMap == null || user == null) {
            return null;
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setDeliveryInfo(deliveryInfoMapper.toEntity(dto.getDeliveryInfo()));
        List<OrderElement> orderElements = dto.getOrderElements().stream()
                .map(orderElementReqDto -> {
                    Product product = productMap.get(orderElementReqDto.getProductId());
                    return orderElementMapper.toEntity(orderElementReqDto, order, product);
                })
                .toList();
        order.setOrderElements(orderElements);

        return order;
    }

    public OrderResDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderResDto dto = new OrderResDto();
        dto.setOrderDate(order.getOrderDate());
        dto.setDeliveryInfo(deliveryInfoMapper.toDto(order.getDeliveryInfo()));
        dto.setOrderElements(order.getOrderElements().stream()
                .map(orderElementMapper::toDto)
                .toList());
        return dto;
    }
}
