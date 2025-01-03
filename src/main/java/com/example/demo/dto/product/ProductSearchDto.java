package com.example.demo.dto.product;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
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
    private String sortOrder = Sort.Direction.ASC.name();
    private Integer page = 0;
    private Integer size = 10;
}
