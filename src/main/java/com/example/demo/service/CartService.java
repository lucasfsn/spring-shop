package com.example.demo.service;

import com.example.demo.dto.cart.CartDto;
import com.example.demo.dto.cart.CartQuantityReqDto;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.exception.NotAvailableException;
import com.example.demo.exception.OutOfStockException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.cart.CartElementMapper;
import com.example.demo.mapper.cart.CartMapper;
import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartElement;
import com.example.demo.model.product.Product;
import com.example.demo.model.user.User;
import com.example.demo.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final CartElementMapper cartElementMapper;

    public CartDto getUserCart(User userDetails) {
        Cart cart = getCartByUsername(userDetails.getUsername());

        return cartMapper.toDto(cart);
    }

    public CartDto addToCart(User userDetails, UUID productId) {
        Cart cart = getCartByUsername(userDetails.getUsername());

        Product product = productService.getProductById(productId);

        if (!product.isAvailable() || product.getQuantity() <= 0) {
            throw new NotAvailableException("Product is not available");
        }

        Optional<CartElement> foundCartElement = cart.getCartElements().stream()
                .filter(cartElement -> cartElement.getProduct().getId().equals(productId))
                .findFirst();

        if (foundCartElement.isPresent()) {
            CartElement cartElement = foundCartElement.get();
            cartElement.setQuantity(cartElement.getQuantity() + 1);
        } else {
            CartElement cartElement = cartElementMapper.toEntity(cart, product, 1);
            cart.getCartElements().add(cartElement);
        }

        product.setQuantity(product.getQuantity() - 1);

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDto(savedCart);
    }

    public CartDto removeFromCart(User userDetails, UUID productId) {
        Cart cart = getCartByUsername(userDetails.getUsername());

        CartElement cartElement = getCartElementByProductIdAndCartId(productId, cart.getId());

        Product product = productService.getProductById(productId);
        product.setQuantity(product.getQuantity() + cartElement.getQuantity());

        cart.getCartElements().remove(cartElement);

        return cartMapper.toDto(cart);
    }

    public CartDto updateQuantity(User userDetails, UUID productId, CartQuantityReqDto cartQuantityReqDto) {
        Cart cart = getCartByUsername(userDetails.getUsername());

        CartElement cartElement = getCartElementByProductIdAndCartId(productId, cart.getId());

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

    public void clearCart(User userDetails) {
        Cart cart = getCartByUsername(userDetails.getUsername());
        cart.getCartElements().clear();
        cartRepository.save(cart);
    }

    private CartElement getCartElementByProductIdAndCartId(UUID productId, UUID cartId) {
        return cartRepository.findCartElementByProductIdAndCartId(productId, cartId).orElseThrow(() -> new ResourceNotFoundException("Cart element not found"));
    }
}
