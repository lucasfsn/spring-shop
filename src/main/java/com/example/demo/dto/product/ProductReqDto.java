package com.example.demo.dto.product;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductReqDto {
    @NotBlank(message = "Product name cannot be empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$", message = "Product name should start with an uppercase letter and contain only letters and spaces")
    @Size(min = 2, max = 25, message = "Product name should be between 2 and 25 characters long")
    private String name;
    @NotBlank(message = "Description cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Description should contain only letters, digits, and spaces")
    @Size(min = 10, max = 125, message = "Description should be between 10 and 125 characters long")
    private String description;
    @Positive(message = "Price must be positive")
    private double price;
    private boolean available;
    @PositiveOrZero(message = "Quantity must be positive or zero")
    private int quantity;
    private List<UUID> categories;
}
