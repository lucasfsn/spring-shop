package com.example.demo.controller.api;

import com.example.demo.dto.category.CategoryReqDto;
import com.example.demo.dto.category.CategoryResDto;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CategoryResDto> createCategory(@Valid @RequestBody CategoryReqDto categoryData) {
        return ResponseEntity.ok(categoryService.createCategory(categoryData));
    }
}
