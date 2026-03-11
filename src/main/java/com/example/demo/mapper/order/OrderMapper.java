package com.example.demo.mapper.order;

import com.example.demo.dto.order.DeliveryInfoDto;
import com.example.demo.dto.order.OrderResDto;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderStatus;
import com.example.demo.model.user.User;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  uses = { DeliveryInfoMapper.class, OrderElementMapper.class },
  imports = { LocalDateTime.class, OrderStatus.class },
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {
  @Mapping(source = "order.orderElements", target = "products")
  @Mapping(source = "totalPrice", target = "totalPrice")
  OrderResDto toDto(Order order, Double totalPrice);

  @Mapping(source = "deliveryInfoDto", target = "deliveryInfo")
  @Mapping(source = "user", target = "user")
  @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
  @Mapping(target = "status", expression = "java(OrderStatus.PENDING)")
  Order toEntity(DeliveryInfoDto deliveryInfoDto, User user);
}
