package com.example.demo.controller.api;

import com.example.demo.dto.order.*;
import com.example.demo.model.user.User;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResDto>> getCategories(@AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(orderService.getOrders(userDetails));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderAdminResDto>> getOrdersFromAllUsers() {
        return ResponseEntity.ok(orderService.getOrdersFromAllUsers());
    }

    @GetMapping("/stats")
    public ResponseEntity<List<OrderStatsDto>> getOrderStats(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return ResponseEntity.ok(orderService.getOrderStats(categoryName, startDate, endDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResDto> getOrder(@PathVariable UUID id, @AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(orderService.getOrder(userDetails, id));
    }

    @PostMapping
    public ResponseEntity<OrderCreateDto> createOrder(@Valid @RequestBody DeliveryInfoDto deliveryInfoDto, @AuthenticationPrincipal User userDetails) {
        OrderCreateDto createdOrder = orderService.createOrder(userDetails, deliveryInfoDto);
        return ResponseEntity.created(URI.create("/api/orders/" + createdOrder.getId())).body(createdOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id, @AuthenticationPrincipal User userDetails) {
        orderService.deleteOrder(userDetails, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResDto> updateOrderStatus(@PathVariable UUID id, @Valid @RequestBody ChangeOrderStatusDto orderStatusDto) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatusDto));
    }
}
