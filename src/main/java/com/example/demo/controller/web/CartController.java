package com.example.demo.controller.web;

import com.example.demo.dto.cart.CartQuantityReqDto;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping
    public String getCart(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("cart", cartService.getUserCart(userDetails));
        return "cart";
    }

    @GetMapping("/products/{productId}/remove")
    public String removeFromCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID productId) {
        cartService.removeFromCart(userDetails, productId);
        return "redirect:/cart";
    }

    @GetMapping("/products/{productId}/add")
    public String addToCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID productId, Model model) {
        try {
            cartService.addToCart(userDetails, productId);
            return "redirect:/cart";
        } catch (Exception e) {
            model.addAttribute("products", productService.getProducts());
            model.addAttribute("error", e.getMessage());
            return "products";
        }
    }

    @PostMapping("/products/{productId}/quantity")
    public String updateQuantity(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID productId, @Valid @ModelAttribute("quantity") CartQuantityReqDto cartQuantityReqDto, Model model) {
        try {
            cartService.updateQuantity(userDetails, productId, cartQuantityReqDto);
            return "redirect:/cart";
        } catch (Exception e) {
            model.addAttribute("cart", cartService.getUserCart(userDetails));
            Map<UUID, String> errors = new HashMap<>();
            errors.put(productId, e.getMessage());
            model.addAttribute("errors", errors);
            return "cart";
        }
    }
}
