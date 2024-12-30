package com.example.demo.mapper.cart;

import com.example.demo.dto.cart.CartElementReqDto;
import com.example.demo.dto.cart.CartElementResDto;
import com.example.demo.dto.product.ProductResDto;
import com.example.demo.mapper.product.ProductMapper;
import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartElement;
import com.example.demo.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartElementMapper {
    private final ProductMapper productMapper;

    public CartElementResDto toDto(CartElement cartElement) {
        if (cartElement == null) {
            return null;
        }
        CartElementResDto dto = new CartElementResDto();
        dto.setId(cartElement.getId());
        dto.setQuantity(cartElement.getQuantity());
        if (cartElement.getProduct() != null) {
            ProductResDto productDto = productMapper.toDto(cartElement.getProduct());
            dto.setProductResDto(productDto);
        }
        return dto;
    }

    public CartElement toEntity(CartElementReqDto cartElementReqDto) {
        if (cartElementReqDto == null) {
            return null;
        }

        CartElement cartElement = new CartElement();
        cartElement.setQuantity(cartElementReqDto.getQuantity());

        if (cartElementReqDto.getProductId() != null) {
            Product product = new Product();
            product.setId(cartElementReqDto.getProductId());
            cartElement.setProduct(product);
        }

        if (cartElementReqDto.getCartId() != null) {
            Cart cart = new Cart();
            cart.setId(cartElementReqDto.getCartId());
            cartElement.setCart(cart);
        }

        return cartElement;
    }
}