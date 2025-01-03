package com.example.demo.controller.web;

import com.example.demo.service.ProductService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Controller
public class HomeController {
    private final ProductService productService;

    @GetMapping("/")
    public String home() {
        return "home";
    }
}