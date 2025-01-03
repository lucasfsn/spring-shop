package com.example.demo.controller.api;

import com.example.demo.dto.cart.CartDto;
import com.example.demo.dto.cart.CartQuantityReqDto;
import com.example.demo.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ApiCartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(cartService.getUserCart(userDetails));
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<CartDto> addToCart(@PathVariable UUID productId, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(cartService.addToCart(userDetails, productId));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<CartDto> removeFromCart(@PathVariable UUID productId, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(cartService.removeFromCart(userDetails, productId));
    }

    @PatchMapping("/products/{productId}")
    public ResponseEntity<CartDto> updateCartElementQuantity(@PathVariable UUID productId, @RequestBody @Valid CartQuantityReqDto cartQuantityReqDto, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(cartService.updateQuantity(userDetails, productId, cartQuantityReqDto));
    }
}
