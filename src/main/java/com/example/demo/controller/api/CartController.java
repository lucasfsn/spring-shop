package com.example.demo.controller.api;

import com.example.demo.dto.cart.CartDto;
import com.example.demo.dto.cart.CartQuantityReqDto;
import com.example.demo.model.user.User;
import com.example.demo.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(cartService.getUserCart(userDetails));
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<CartDto> addToCart(@PathVariable UUID productId, @AuthenticationPrincipal User userDetails) {
        return ResponseEntity.status(201).body(cartService.addToCart(userDetails, productId));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<CartDto> removeFromCart(@PathVariable UUID productId, @AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(cartService.removeFromCart(userDetails, productId));
    }

    @PatchMapping("/products/{productId}")
    public ResponseEntity<CartDto> updateCartElementQuantity(@PathVariable UUID productId, @Valid @RequestBody CartQuantityReqDto cartQuantityReqDto, @AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(cartService.updateQuantity(userDetails, productId, cartQuantityReqDto));
    }
}
