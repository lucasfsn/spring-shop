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
import com.example.demo.model.user.User;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Map<UUID, List<OrderWithTotalPriceDto>> userOrdersMap = new HashMap<>();

        for (Object[] result : results) {
            UUID userId = (UUID) result[0];
            Order order = (Order) result[1];
            Double totalPrice = (Double) result[2];
            OrderWithTotalPriceDto orderWithTotalPrice = OrderWithTotalPriceDto.builder()
                    .order(order)
                    .totalPrice(totalPrice)
                    .build();
            userOrdersMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(orderWithTotalPrice);
        }

        return userOrdersMap.entrySet().stream()
                .map(entry -> {
                    UUID userId = entry.getKey();
                    List<OrderResDto> ordersDto = entry.getValue().stream()
                            .map(orderWithTotalPrice -> orderMapper.toDto(orderWithTotalPrice.getOrder(), orderWithTotalPrice.getTotalPrice()))
                            .toList();
                    return OrderAdminResDto.builder()
                            .userId(userId)
                            .orders(ordersDto)
                            .build();
                })
                .toList();
    }

    public List<OrderStatsDto> getOrderStats(String categoryName, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(23, 59, 59) : null;

        if (start != null && end != null && end.isBefore(start)) {
            throw new InvalidDataException("End date must be greater than or equal to start date");
        }

        return orderRepository.findOrderStatsByCategoryAndCreatedWithinDateRange(categoryName, start, end);
    }

    public List<OrderResDto> getOrders(User userDetails) {
        List<Object[]> results = orderRepository.findOrdersByUsername(userDetails.getUsername());
        return results.stream()
                .map(result -> orderMapper.toDto((Order) result[0], (Double) result[1]))
                .toList();
    }

    public OrderResDto getOrder(User userDetails, UUID id) {
        OrderWithTotalPriceDto order = getOrderByID(id);

        if (!order.getOrder().getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("You cannot access this order");
        }

        return orderMapper.toDto(order.getOrder(), order.getTotalPrice());
    }

    public OrderCreateDto createOrder(User userDetails, DeliveryInfoDto deliveryInfoDto) {
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
        cartService.clearCart(userDetails);
        return OrderCreateDto.builder()
                .id(savedOrder.getId())
                .build();
    }

    public void deleteOrder(User userDetails, UUID id) {
        OrderWithTotalPriceDto order = getOrderByID(id);

        if (userDetails.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) && !order.getOrder().getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("You do not have access to cancel this order");
        }

        if (order.getOrder().getStatus() != OrderStatus.PENDING) {
            throw new InvalidDataException("You cannot cancel this order because it is confirmed and already processed");
        }

        for (OrderElement orderElement : order.getOrder().getOrderElements()) {
            productService.updateProductQuantity(orderElement.getProduct().getId(), orderElement.getQuantity());
        }

        orderRepository.deleteById(order.getOrder().getId());
    }

    public OrderResDto updateOrderStatus(UUID id, ChangeOrderStatusDto orderStatusDto) {
        OrderWithTotalPriceDto order = getOrderByID(id);
        order.getOrder().setStatus(orderStatusDto.getStatus());
        Order updatedOrder = orderRepository.save(order.getOrder());
        return orderMapper.toDto(updatedOrder, order.getTotalPrice());
    }

    private OrderWithTotalPriceDto getOrderByID(UUID id) {
        return orderRepository.findOrderWithTotalPrice(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}
