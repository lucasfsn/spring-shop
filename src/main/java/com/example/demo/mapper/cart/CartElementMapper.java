package com.example.demo.mapper.cart;

import com.example.demo.dto.cart.CartElementResDto;
import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartElement;
import com.example.demo.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartElementMapper {
  @Mapping(source = "product.id", target = "productId")
  @Mapping(source = "product.name", target = "productName")
  @Mapping(source = "product.description", target = "productDescription")
  @Mapping(source = "product.price", target = "productPrice")
  @Mapping(source = "product.categories", target = "productCategories")
  CartElementResDto toDto(CartElement cartElement);

  @Mapping(target = "id", ignore = true)
  @Mapping(source = "cart", target = "cart")
  @Mapping(source = "product", target = "product")
  @Mapping(source = "quantity", target = "quantity")
  CartElement toEntity(Cart cart, Product product, int quantity);
}
