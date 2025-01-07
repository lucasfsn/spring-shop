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
public class WebOrderController {
    private final OrderService orderService;

    @GetMapping
    public String getOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("orders", orderService.getOrders(userDetails));
        model.addAttribute("error", null);
        return "orders";
    }

    @GetMapping("/create")
    public String createOrder(Model model) {
        model.addAttribute("deliveryInfoForm", new DeliveryInfoDto());
        return "delivery-form";
    }

    @PostMapping("/create")
    public String createOrderForm(@Valid @ModelAttribute("deliveryInfoForm") DeliveryInfoDto deliveryInfoDto, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("deliveryInfoForm", deliveryInfoDto);
            return "delivery-form";
        }

        try {
            orderService.createOrder(userDetails, deliveryInfoDto);
            return "redirect:/orders";
        } catch (Exception e) {
            model.addAttribute("deliveryInfoForm", deliveryInfoDto);
            model.addAttribute("error", e.getMessage());
            return "delivery-form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable UUID id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            orderService.deleteOrder(userDetails, id);
            return "redirect:/orders";
        } catch (Exception e) {
            model.addAttribute("orders", orderService.getOrders(userDetails));
            model.addAttribute("error", e.getMessage());
            return "orders";
        }
    }
}
