package com.example.demo.model.product;

import com.example.demo.model.cart.CartElement;
import com.example.demo.model.category.Category;
import com.example.demo.model.order.OrderElement;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$", message = "Name should start with an uppercase letter and contain only letters and spaces")
    @Size(min = 2, max = 25, message = "Name should be between 2 and 25 characters long")
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

    @OneToMany(mappedBy = "product")
    private List<CartElement> cartElements;

    @OneToMany(mappedBy = "product")
    private List<OrderElement> orderElements;

    @ManyToMany
    private List<Category> categories;
}
