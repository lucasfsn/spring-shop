package com.example.demo.controller.web;

import com.example.demo.dto.category.CategoryReqDto;
import com.example.demo.dto.user.ChangeUserRoleReqDto;
import com.example.demo.model.user.UserRole;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;
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
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("userRoleChangeForm", new ChangeUserRoleReqDto());
        model.addAttribute("categoryAddForm", new CategoryReqDto());
        model.addAttribute("roles", UserRole.values());
        return "admin";
    }

    @PostMapping("/change-role")
    public String changeUserRole(@RequestParam UUID userId, @Valid @ModelAttribute("userRoleChangeForm") ChangeUserRoleReqDto changeUserRoleReqDto, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRoleChangeForm", changeUserRoleReqDto);
            model.addAttribute("categoryAddForm", new CategoryReqDto());
            model.addAttribute("roles", UserRole.values());
            return "admin";
        }

        try {
            userService.changeUserRole(userDetails, userId, changeUserRoleReqDto);
            return "redirect:/admin";
        } catch (Exception e) {
            model.addAttribute("userRoleChangeForm", changeUserRoleReqDto);
            model.addAttribute("categoryAddForm", new CategoryReqDto());
            model.addAttribute("roles", UserRole.values());
            model.addAttribute("roleError", e.getMessage());
            return "admin";
        }
    }

    @PostMapping("/add-category")
    public String addCategory(@Valid @ModelAttribute("categoryAddForm") CategoryReqDto categoryReqDto, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRoleChangeForm", new ChangeUserRoleReqDto());
            model.addAttribute("categoryAddForm", categoryReqDto);
            model.addAttribute("roles", UserRole.values());
            return "admin";
        }

        try {
            categoryService.createCategory(userDetails, categoryReqDto);
            return "redirect:/admin";
        } catch (Exception e) {
            model.addAttribute("userRoleChangeForm", new ChangeUserRoleReqDto());
            model.addAttribute("categoryAddForm", categoryReqDto);
            model.addAttribute("roles", UserRole.values());
            model.addAttribute("categoryError", e.getMessage());
            return "admin";
        }
    }
}
