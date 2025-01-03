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
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthService authService;

    public void deleteProduct(UserDetails userDetails, UUID id) {
        authService.hasAdminAuthority(userDetails);

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

    public ProductResDto updateProduct(UserDetails userDetails, UUID id, ProductReqDto productData) {
        authService.hasAdminAuthority(userDetails);

        List<Category> categories = categoryRepository.findAllById(productData.getCategories());

        if (categories.size() != productData.getCategories().size()) {
            throw new ResourceNotFoundException("All categories have not been found");
        }
        
        Product updatedProduct = productMapper.toExistingEntity(getProductById(id), productData, categories);
        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.toDto(savedProduct);
    }

    public ProductResDto createProduct(UserDetails userDetails, ProductReqDto productData) {
        authService.hasAdminAuthority(userDetails);

        List<Category> categories = categoryRepository.findAllById(productData.getCategories());
        if (categories.size() != productData.getCategories().size()) {
            throw new ResourceNotFoundException("All categories have not been found");
        }

        Product product = productMapper.toEntity(productData, categories);
        Product createdProduct = productRepository.save(product);
        return productMapper.toDto(createdProduct);
    }

    public void updateProductQuantity(UUID productID, int quantity) {
        Product product = getProductById(productID);
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
}
