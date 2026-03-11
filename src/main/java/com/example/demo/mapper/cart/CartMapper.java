package com.example.demo.mapper.cart;

import com.example.demo.dto.cart.CartDto;
import com.example.demo.model.cart.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { CartElementMapper.class })
public interface CartMapper {
  @Mapping(source = "cartElements", target = "elements")
  CartDto toDto(Cart cart);
}
