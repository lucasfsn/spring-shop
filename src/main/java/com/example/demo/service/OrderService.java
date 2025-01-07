package com.example.demo.service;

import com.example.demo.dto.order.*;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.order.OrderElementMapper;
import com.example.demo.mapper.order.OrderMapper;
import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartElement;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderElement;
import com.example.demo.model.order.OrderStatus;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderElementMapper orderElementMapper;
    private final CartService cartService;
    private final ProductService productService;

    public List<OrderAdminResDto> getOrdersFromAllUsers() {
        List<Object[]> results = orderRepository.findAllUsersOrders();
        Map<UUID, List<Order>> userOrdersMap = new HashMap<>();

        for (Object[] result : results) {
            UUID userId = (UUID) result[0];
            Order order = (Order) result[1];
            userOrdersMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(order);
        }

        return userOrdersMap.entrySet().stream()
                .map(entry -> {
                    UUID userId = entry.getKey();
                    List<OrderResDto> ordersDto = entry.getValue().stream()
                            .map(this::orderResDtoWithTotalPrice)
                            .toList();
                    return new OrderAdminResDto(userId, ordersDto);
                })
                .toList();
    }

    public List<OrderResDto> getOrders(UserDetails userDetails) {
        List<Order> orders = getOrdersByUsername(userDetails.getUsername());

        return orders.stream().map(this::orderResDtoWithTotalPrice).toList();
    }

    public OrderResDto getOrder(UserDetails userDetails, UUID id) {
        Order order = getOrderByID(id);

        if (!order.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("You cannot access this order");
        }

        return orderResDtoWithTotalPrice(order);
    }

    public OrderCreateDto createOrder(UserDetails userDetails, DeliveryInfoDto deliveryInfoDto) {
        Cart cart = cartService.getCartByUsername(userDetails.getUsername());
        List<CartElement> cartElements = cart.getCartElements();

        if (cartElements.isEmpty()) {
            throw new InvalidDataException("Cart is empty");
        }

        Order order = orderMapper.toEntity(deliveryInfoDto, cart.getUser());

        List<OrderElement> orderElements = cartElements.stream()
                .map(cartElement -> orderElementMapper.toEntity(cartElement, order))
                .toList();

        order.setOrderElements(orderElements);
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userDetails.getUsername());
        return new OrderCreateDto(savedOrder.getId());
    }

    public void deleteOrder(UserDetails userDetails, UUID id) {
        Order order = getOrderByID(id);

        if (!order.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("You do not have access to cancel this order");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidDataException("You cannot cancel this order because it is confirmed and already processed");
        }

        for (OrderElement orderElement : order.getOrderElements()) {
            productService.updateProductQuantity(orderElement.getProduct().getId(), orderElement.getQuantity());
        }

        orderRepository.deleteById(order.getId());
    }

    public OrderResDto updateOrderStatus(UUID id, ChangeOrderStatusDto orderStatusDto) {
        Order order = getOrderByID(id);
        order.setStatus(orderStatusDto.getStatus());
        Order updatedOrder = orderRepository.save(order);
        return orderResDtoWithTotalPrice(updatedOrder);
    }

    private List<Order> getOrdersByUsername(String username) {
        return orderRepository.findOrdersByUsername(username);
    }

    private Order getOrderByID(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    private OrderResDto orderResDtoWithTotalPrice(Order order) {
        OrderResDto dto = orderMapper.toDto(order);
        dto.setTotalPrice(orderRepository.findTotalPriceByOrderId(order.getId()));
        return dto;
    }
}
