package com.example.demo.service;

import com.example.demo.dto.order.DeliveryInfoDto;
import com.example.demo.dto.order.OrderResDto;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.order.DeliveryInfoMapper;
import com.example.demo.mapper.order.OrderElementMapper;
import com.example.demo.mapper.order.OrderMapper;
import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartElement;
import com.example.demo.model.order.DeliveryInfo;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderElement;
import com.example.demo.model.product.Product;
import com.example.demo.repository.DeliveryInfoRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final DeliveryInfoMapper deliveryInfoMapper;
    private final OrderElementMapper orderElementMapper;
    private final DeliveryInfoRepository deliveryInfoRepository;
    private final CartService cartService;

    public List<OrderResDto> getOrders(UserDetails userDetails) {
        List<Order> orders = getOrdersByUsername(userDetails.getUsername());

        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderResDto getOrder(UserDetails userDetails, UUID id) {
        Order order = getOrderByID(id);

        if (!order.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("You cannot access this order");
        }

        return orderMapper.toDto(order);
    }

    public OrderResDto createOrder(UserDetails userDetails, DeliveryInfoDto deliveryInfoDto) {
        DeliveryInfo deliveryInfo = deliveryInfoRepository.save(deliveryInfoMapper.toEntity(deliveryInfoDto));
        Cart cart = cartService.getCartByUsername(userDetails.getUsername());

        List<CartElement> cartElements = cart.getCartElements();

        if (cartElements.isEmpty()) {
            throw new InvalidDataException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setDeliveryInfo(deliveryInfo);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderElement> orderElements = cartElements.stream().map(cartElement -> orderElementMapper.toEntity(order, cartElement.getProduct(), cartElement.getQuantity())
        ).toList();

        order.setOrderElements(orderElements);
        cartService.clearCart(userDetails.getUsername());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    public void deleteOrder(UserDetails userDetails, UUID id) {
        Order order = getOrderByID(id);

        if (!order.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("You cannot access this order");
        }

        for (OrderElement orderElement : order.getOrderElements()) {
            Product product = orderElement.getProduct();
            product.setQuantity(product.getQuantity() + orderElement.getQuantity());
        }

        orderRepository.deleteById(order.getId());
    }

    private List<Order> getOrdersByUsername(String username) {
        return orderRepository.findOrdersByUsername(username);
    }

    private Order getOrderByID(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}
