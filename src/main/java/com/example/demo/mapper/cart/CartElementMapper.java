package com.example.demo.mapper.cart;

import com.example.demo.dto.cart.CartElementResDto;
import com.example.demo.dto.category.CategoryResDto;
import com.example.demo.mapper.category.CategoryMapper;
import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartElement;
import com.example.demo.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartElementMapper {
    private final CategoryMapper categoryMapper;

    public CartElementResDto toDto(CartElement cartElement) {
        if (cartElement == null) {
            return null;
        }

        Product product = cartElement.getProduct();

        List<CategoryResDto> categories = product.getCategories().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());

        return CartElementResDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productDescription(product.getDescription())
                .productPrice(product.getPrice())
                .quantity(cartElement.getQuantity())
                .productCategories(categories).build();
    }

    public CartElement toEntity(Cart cart, Product product, int quantity) {
        if (cart == null || product == null) {
            return null;
        }

        return CartElement.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();
    }
}