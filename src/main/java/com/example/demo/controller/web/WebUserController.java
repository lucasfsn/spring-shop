package com.example.demo.controller.web;

import com.example.demo.dto.user.SearchUserResDto;
import com.example.demo.dto.user.UserResDto;
import com.example.demo.service.UserService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Controller
@RequestMapping("/users")
public class WebUserController {
    private final UserService userService;

    @GetMapping("{id}")
    public String getUser(@PathVariable("id") UUID id, Model model) {
        UserResDto user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam(value = "pattern", defaultValue = "") String pattern, Model model) {
        List<SearchUserResDto> users = pattern.isEmpty() ? new ArrayList<>() : userService.searchUsers(pattern);
        model.addAttribute("users", users);
        return "usersSearch";
    }
}
