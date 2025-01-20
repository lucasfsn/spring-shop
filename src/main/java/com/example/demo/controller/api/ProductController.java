package com.example.demo.controller.api;

import com.example.demo.dto.product.ProductOrderStatsDto;
import com.example.demo.dto.product.ProductReqDto;
import com.example.demo.dto.product.ProductResDto;
import com.example.demo.dto.product.ProductSearchDto;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResDto>> getProducts(ProductSearchDto paramsDto) {
        return ResponseEntity.ok(productService.getProducts(paramsDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResDto> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping("/orders/stats")
    public ResponseEntity<List<ProductOrderStatsDto>> getProductOrderStats(@RequestParam(required = false) String categoryName, @RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate) {
        return ResponseEntity.ok(productService.getProductOrderStats(categoryName, startDate, endDate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResDto> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductReqDto productData) {
        return ResponseEntity.ok(productService.updateProduct(id, productData));
    }

    @PostMapping
    public ResponseEntity<ProductResDto> createProduct(@Valid @RequestBody ProductReqDto productData) {
        ProductResDto createdProduct = productService.createProduct(productData);
        return ResponseEntity.created(URI.create("/api/products/" + createdProduct.getId())).body(createdProduct);
    }
}
