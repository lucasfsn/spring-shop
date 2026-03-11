package com.example.demo.mapper.product;

import com.example.demo.dto.product.ProductReqDto;
import com.example.demo.dto.product.ProductResDto;
import com.example.demo.mapper.category.CategoryMapper;
import com.example.demo.model.category.Category;
import com.example.demo.model.product.Product;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  uses = { CategoryMapper.class },
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {
  ProductResDto toDto(Product product);

  @Mapping(target = "categories", source = "categories")
  Product toEntity(ProductReqDto productDto, List<Category> categories);

  @Mapping(target = "categories", source = "categories")
  Product toExistingEntity(
    @MappingTarget Product product,
    ProductReqDto productDto,
    List<Category> categories
  );
}
