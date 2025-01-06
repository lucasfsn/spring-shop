package com.example.demo.controller.web;

import com.example.demo.dto.user.AuthResDto;
import com.example.demo.dto.user.UserLoginReqDto;
import com.example.demo.dto.user.UserSignupReqDto;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Controller
@RequestMapping("/auth")
public class WebAuthController {
    private final AuthService authService;

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("signupForm", new UserSignupReqDto());
        return "signup-form";
    }

    @PostMapping("/signup")
    public String signupUser(@Valid @ModelAttribute("signupForm") UserSignupReqDto user, BindingResult bindingResult, Model model, HttpServletResponse response, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("signupForm", user);
            return "signup-form";
        }

        try {
            AuthResDto authResDto = authService.signUp(user);

            Cookie cookie = new Cookie("auth_token", authResDto.getToken());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(10 * 60 * 60);
            response.addCookie(cookie);

            httpSession.setAttribute("user", authResDto.getUser());

            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("signupForm", user);
            model.addAttribute("error", e.getMessage());
            return "signup-form";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new UserLoginReqDto());
        return "login-form";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("loginForm") UserLoginReqDto user, BindingResult bindingResult, Model model, HttpServletResponse response, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginForm", user);
            return "login-form";
        }
        try {
            AuthResDto authResDto = authService.authenticate(user);
            Cookie cookie = new Cookie("auth_token", authResDto.getToken());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(10 * 60 * 60);
            response.addCookie(cookie);

            httpSession.setAttribute("user", authResDto.getUser());

            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("loginForm", user);
            model.addAttribute("error", e.getMessage());
            return "login-form";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();

        Cookie cookie = new Cookie("auth_token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
