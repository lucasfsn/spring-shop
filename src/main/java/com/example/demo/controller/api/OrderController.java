package com.example.demo.controller.api;

import com.example.demo.dto.order.*;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResDto>> getCategories(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(orderService.getOrders(userDetails));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderAdminResDto>> getOrdersFromAllUsers() {
        return ResponseEntity.ok(orderService.getOrdersFromAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResDto> getOrder(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(orderService.getOrder(userDetails, id));
    }

    @PostMapping
    public ResponseEntity<OrderCreateDto> createOrder(@Valid @RequestBody DeliveryInfoDto deliveryInfoDto, @AuthenticationPrincipal UserDetails userDetails) {
        OrderCreateDto createdOrder = orderService.createOrder(userDetails, deliveryInfoDto);
        return ResponseEntity.created(URI.create("/api/orders/" + createdOrder.getId())).body(createdOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        orderService.deleteOrder(userDetails, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResDto> updateOrderStatus(@PathVariable UUID id, @Valid @RequestBody ChangeOrderStatusDto orderStatusDto) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatusDto));
    }
}
