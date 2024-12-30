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
        CategoryResDto categoryDto = new CategoryResDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public Category toEntity(CategoryReqDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }
}
