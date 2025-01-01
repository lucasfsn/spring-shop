package com.example.demo.service;

import com.example.demo.dto.product.ProductReqDto;
import com.example.demo.dto.product.ProductResDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.product.ProductMapper;
import com.example.demo.model.category.Category;
import com.example.demo.model.product.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public void deleteProduct(UUID id) {
        Product product = getProductById(id);

        productRepository.deleteById(product.getId());
    }

    public ProductResDto getProduct(UUID id) {
        Product product = getProductById(id);

        return productMapper.toDto(product);
    }

    public List<ProductResDto> getProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductResDto updateProduct(UUID id, ProductReqDto productData) {
        Product product = getProductById(id);

        List<UUID> categoryIds = productData.getCategories();
        List<Category> categories = categoryRepository.findAllById(categoryIds);

        if (categories.size() != categoryIds.size()) {
            throw new ResourceNotFoundException("Some categories not found");
        }

        product.setName(productData.getName());
        product.setDescription(productData.getDescription());
        product.setPrice(productData.getPrice());
        product.setAvailable(productData.isAvailable());
        product.setQuantity(productData.getQuantity());
        product.setCategories(categories);

        Product updatedProduct = productRepository.save(product);
        return productMapper.toDto(updatedProduct);
    }

    public ProductResDto createProduct(ProductReqDto productData) {
        Product product = productMapper.toEntity(productData);
        List<UUID> categoryIds = productData.getCategories();
        List<Category> categories = categoryRepository.findAllById(categoryIds);

        if (categories.size() != categoryIds.size()) {
            throw new ResourceNotFoundException("Categories not found");
        }

        product.setCategories(categories);

        Product createdProduct = productRepository.save(product);
        return productMapper.toDto(createdProduct);
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
}
