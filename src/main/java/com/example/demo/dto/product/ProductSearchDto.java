package com.example.demo.dto.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class ProductSearchDto {
    private String name;
    private String description;
    private String category;
    private Double minPrice;
    private Double maxPrice;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Boolean available;
    private String sortBy = "name";
    private Sort.Direction sortOrder = Sort.Direction.ASC;
    private Integer page = 0;
    private Integer size = 10;
}
