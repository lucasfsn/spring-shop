package com.example.demo.controller.web;

import com.example.demo.dto.order.DeliveryInfoDto;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Data
@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public String getOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("orders", orderService.getOrders(userDetails));
        return "orders";
    }

    @GetMapping("/create")
    public String createOrder(Model model) {
        model.addAttribute("deliveryInfoForm", new DeliveryInfoDto());
        return "delivery-form";
    }

    @PostMapping("/create")
    public String createOrderForm(@Valid @ModelAttribute("deliveryInfoForm") DeliveryInfoDto deliveryInfoDto, Model model, @AuthenticationPrincipal UserDetails userDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("deliveryInfoForm", new DeliveryInfoDto());
            return "delivery-form";
        }

        try {
            orderService.createOrder(userDetails, deliveryInfoDto);
            return "redirect:/orders";
        } catch (Exception e) {
            model.addAttribute("deliveryInfoForm", new DeliveryInfoDto());
            return "delivery-form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id) {
        orderService.deleteOrder(userDetails, id);
        return "redirect:/orders";
    }
}
