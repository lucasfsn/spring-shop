package com.example.demo.mapper.cart;

import com.example.demo.dto.cart.CartDto;
import com.example.demo.dto.cart.CartElementResDto;
import com.example.demo.model.cart.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final CartElementMapper cartElementMapper;

    public CartDto toDto(Cart cart) {
        if (cart == null) {
            return null;
        }

        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        List<CartElementResDto> cartElements = cart.getCartElements().stream()
                .map(cartElementMapper::toDto)
                .collect(Collectors.toList());
        dto.setElements(cartElements);
        return dto;
    }
}
