package com.example.demo.mapper.category;

import com.example.demo.dto.category.CategoryReqDto;
import com.example.demo.dto.category.CategoryResDto;
import com.example.demo.model.category.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResDto toDto(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category toEntity(CategoryReqDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }
}
