package com.example.demo.mapper.product;

import com.example.demo.dto.category.CategoryResDto;
import com.example.demo.dto.product.ProductReqDto;
import com.example.demo.dto.product.ProductResDto;
import com.example.demo.mapper.category.CategoryMapper;
import com.example.demo.model.category.Category;
import com.example.demo.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;

    public ProductResDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        List<CategoryResDto> categoriesDto = null;
        if (product.getCategories() != null) {
            categoriesDto = product.getCategories()
                    .stream()
                    .map(categoryMapper::toDto)
                    .toList();
        }

        return ProductResDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .available(product.isAvailable())
                .quantity(product.getQuantity())
                .categories(categoriesDto)
                .build();
    }

    public Product toEntity(ProductReqDto productDto, List<Category> categories) {
        if (productDto == null) {
            return null;
        }

        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .available(productDto.isAvailable())
                .quantity(productDto.getQuantity())
                .categories(categories)
                .build();
    }

    public Product toExistingEntity(Product product, ProductReqDto productDto, List<Category> categories) {
        if (productDto == null) {
            return null;
        }

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setAvailable(productDto.isAvailable());
        product.setQuantity(productDto.getQuantity());
        product.setCategories(categories);
        return product;
    }
}