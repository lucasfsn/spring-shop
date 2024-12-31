package com.example.demo.controller.web;

import com.example.demo.dto.user.UserResDto;
import com.example.demo.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Controller
public class HomeController {
    private final ProductService productService;

    @GetMapping("/")
    public String home(HttpSession httpSession) {
        UserResDto user = (UserResDto) httpSession.getAttribute("user");
        return "home";
    }
}