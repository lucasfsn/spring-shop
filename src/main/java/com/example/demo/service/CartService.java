package com.example.demo.service;

import com.example.demo.dto.cart.CartDto;
import com.example.demo.dto.cart.CartQuantityReqDto;
import com.example.demo.exception.*;
import com.example.demo.mapper.cart.CartMapper;
import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartElement;
import com.example.demo.model.product.Product;
import com.example.demo.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductService productService;

    public CartDto getUserCart(UserDetails userDetails) {
        Cart cart = getCartByUsername(userDetails.getUsername());

        return cartMapper.toDto(cart);
    }

    public CartDto addToCart(UserDetails userDetails, UUID productId) {
        Cart cart = getCartByUsername(userDetails.getUsername());

        if (!cart.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("You cannot add products to this cart");
        }

        Product product = productService.getProductById(productId);

        if (!product.isAvailable()) {
            throw new NotAvailableException("Product is not available");
        }

        if (product.getQuantity() <= 0) {
            throw new OutOfStockException("Not enough products in stock");
        }

        Optional<CartElement> foundCartElement = cart.getCartElements().stream()
                .filter(cartElement -> cartElement.getProduct().getId().equals(productId))
                .findFirst();

        if (foundCartElement.isPresent()) {
            CartElement cartElement = foundCartElement.get();
            cartElement.setQuantity(cartElement.getQuantity() + 1);
        } else {
            CartElement cartElement = new CartElement();
            cartElement.setCart(cart);
            cartElement.setProduct(product);
            cartElement.setQuantity(1);
            cart.getCartElements().add(cartElement);
        }

        product.setQuantity(product.getQuantity() - 1);

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDto(savedCart);
    }

    public CartDto removeFromCart(UserDetails userDetails, UUID productId) {
        Cart cart = getCartByUsername(userDetails.getUsername());
        UUID cartId = cart.getId();

        CartElement cartElement = getCartElementByProductIdAndCartId(productId, cartId);

        Product product = productService.getProductById(productId);
        product.setQuantity(product.getQuantity() + cartElement.getQuantity());

        cart.getCartElements().remove(cartElement);

        return cartMapper.toDto(cart);
    }

    public CartDto updateQuantity(UserDetails userDetails, UUID productId, CartQuantityReqDto cartQuantityReqDto) {
        Cart cart = getCartByUsername(userDetails.getUsername());
        UUID cartId = cart.getId();

        CartElement cartElement = getCartElementByProductIdAndCartId(productId, cartId);

        Product product = productService.getProductById(productId);

        int quantity = cartQuantityReqDto.getQuantity();

        if (quantity <= 0) {
            throw new InvalidDataException("Quantity must be greater than 0");
        }

        if (quantity > product.getQuantity() + cartElement.getQuantity()) {
            throw new OutOfStockException("Not enough products in stock");
        }

        product.setQuantity(product.getQuantity() - (quantity - cartElement.getQuantity()));
        cartElement.setQuantity(quantity);

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDto(savedCart);
    }

    public Cart getCartByUsername(String username) {
        return cartRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Cart not found for this user"));
    }

    public void clearCart(String username) {
        Cart cart = getCartByUsername(username);
        cart.getCartElements().clear();
        cartRepository.save(cart);
    }

    private CartElement getCartElementByProductIdAndCartId(UUID productId, UUID cartId) {
        return cartRepository.findCartElementByProductIdAndCartId(productId, cartId).orElseThrow(() -> new ResourceNotFoundException("Cart element not found"));
    }
}
