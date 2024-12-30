package com.example.demo.mapper.product;

import com.example.demo.dto.product.ProductReqDto;
import com.example.demo.dto.product.ProductResDto;
import com.example.demo.dto.category.CategoryResDto;
import com.example.demo.mapper.category.CategoryMapper;
import com.example.demo.model.product.Product;
import com.example.demo.model.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;

    public ProductResDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductResDto productDto = new ProductResDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setAvailable(product.isAvailable());
        productDto.setQuantity(product.getQuantity());

        if (product.getCategories() != null) {
            List<CategoryResDto> categoryDtos = product.getCategories()
                    .stream()
                    .map(categoryMapper::toDto)
                    .collect(Collectors.toList());
            productDto.setCategories(categoryDtos);
        }

        return productDto;
    }

    public Product toEntity(ProductReqDto productDto) {
        if (productDto == null) {
            return null;
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setAvailable(productDto.isAvailable());
        product.setQuantity(productDto.getQuantity());

        if (productDto.getCategories() != null) {
            List<Category> categories = productDto.getCategories()
                    .stream()
                    .map(categoryMapper::toEntity)
                    .collect(Collectors.toList());
            product.setCategories(categories);
        }

        return product;
    }
}