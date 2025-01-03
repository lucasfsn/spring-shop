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
        CartElementResDto dto = new CartElementResDto();

        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setProductDescription(product.getDescription());
        dto.setProductPrice(product.getPrice());
        dto.setQuantity(cartElement.getQuantity());

        List<CategoryResDto> categories = product.getCategories().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
        dto.setProductCategories(categories);

        return dto;
    }

    public CartElement toEntity(Cart cart, Product product, int quantity) {
        if (cart == null || product == null) {
            return null;
        }

        CartElement cartElement = new CartElement();
        cartElement.setCart(cart);
        cartElement.setProduct(product);
        cartElement.setQuantity(quantity);
        return cartElement;
    }
}