package com.example.demo.controller.api;

import com.example.demo.dto.product.ProductReqDto;
import com.example.demo.dto.product.ProductResDto;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ApiProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResDto> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        productService.deleteProduct(userDetails, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResDto> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductReqDto productData, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(productService.updateProduct(userDetails, id, productData));
    }

    @PostMapping
    public ResponseEntity<ProductResDto> createProduct(@RequestBody @Valid ProductReqDto productData, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(productService.createProduct(userDetails, productData));
    }
}
