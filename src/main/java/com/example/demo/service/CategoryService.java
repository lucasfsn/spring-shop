package com.example.demo.service;

import com.example.demo.dto.category.CategoryReqDto;
import com.example.demo.dto.category.CategoryResDto;
import com.example.demo.exception.AlreadyExistException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.category.CategoryMapper;
import com.example.demo.model.category.Category;
import com.example.demo.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AuthService authService;

    public void deleteCategory(UserDetails userDetails, UUID id) {
        authService.hasAdminAuthority(userDetails);

        Category category = getCategoryById(id);

        categoryRepository.deleteById(category.getId());
    }

    public CategoryResDto createCategory(UserDetails userDetails, CategoryReqDto categoryData) {
        authService.hasAdminAuthority(userDetails);

        if (categoryRepository.existsByName(categoryData.getName())) {
            throw new AlreadyExistException("Category with this name already exists");
        }

        Category category = categoryMapper.toEntity(categoryData);
        Category createdCategory = categoryRepository.save(category);
        return categoryMapper.toDto(createdCategory);
    }

    public List<CategoryResDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    private Category getCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
