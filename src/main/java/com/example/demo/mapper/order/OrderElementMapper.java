package com.example.demo.mapper.order;

import com.example.demo.dto.order.OrderProductDto;
import com.example.demo.model.cart.CartElement;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderElement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderElementMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "cartElement.product", target = "product")
  @Mapping(source = "order", target = "order")
  @Mapping(source = "cartElement.quantity", target = "quantity")
  OrderElement toEntity(CartElement cartElement, Order order);

  @Mapping(source = "product.id", target = "id")
  @Mapping(source = "product.name", target = "name")
  @Mapping(source = "product.description", target = "description")
  @Mapping(source = "product.price", target = "price")
  OrderProductDto toDto(OrderElement entity);
}
