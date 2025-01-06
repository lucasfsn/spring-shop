package com.example.demo.service;

import com.example.demo.dto.product.ProductReqDto;
import com.example.demo.dto.product.ProductResDto;
import com.example.demo.dto.product.ProductSearchDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.product.ProductMapper;
import com.example.demo.model.category.Category;
import com.example.demo.model.product.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<ProductResDto> getProducts(ProductSearchDto searchDto) {
        Pageable pageable = PageRequest.of(
                searchDto.getPage(),
                searchDto.getSize(),
                Sort.by(searchDto.getSortOrder(), searchDto.getSortBy())
        );
        Page<Product> products = productRepository.searchProducts(
                searchDto.getName(),
                searchDto.getDescription(),
                searchDto.getCategory(),
                searchDto.getMinPrice(),
                searchDto.getMaxPrice(),
                searchDto.getMinQuantity(),
                searchDto.getMaxQuantity(),
                searchDto.getAvailable(),
                pageable
        );
        return products.map(productMapper::toDto);
    }

    public ProductResDto updateProduct(UUID id, ProductReqDto productData) {
        List<Category> categories = categoryRepository.findAllById(productData.getCategories());

        if (categories.size() != productData.getCategories().size()) {
            throw new ResourceNotFoundException("All categories have not been found");
        }

        Product updatedProduct = productMapper.toExistingEntity(getProductById(id), productData, categories);
        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.toDto(savedProduct);
    }

    public ProductResDto createProduct(ProductReqDto productData) {
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
