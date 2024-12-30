package com.example.demo.controller;

import com.example.demo.dto.user.UpdateUserReqDto;
import com.example.demo.dto.user.UserResDto;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/{id}")
    public ResponseEntity<UserResDto> updateUser(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UpdateUserReqDto updateReqDto) {
        return ResponseEntity.ok(userService.updateUser(id, updateReqDto));
    }
}
