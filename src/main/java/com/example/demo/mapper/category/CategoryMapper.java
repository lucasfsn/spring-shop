package com.example.demo.mapper.category;

import com.example.demo.dto.category.CategoryReqDto;
import com.example.demo.dto.category.CategoryResDto;
import com.example.demo.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryMapper {
  CategoryResDto toDto(Category category);
  Category toEntity(CategoryReqDto categoryReqDto);
}
