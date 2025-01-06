package com.example.demo.controller.api;

import com.example.demo.dto.user.ChangeUserRoleReqDto;
import com.example.demo.dto.user.UpdateUserDto;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResDto> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResDto> changeUserRole(@PathVariable UUID id, @Valid @RequestBody ChangeUserRoleReqDto role) {
        return ResponseEntity.ok(userService.changeUserRole(id, role));
    }

    @PutMapping
    public ResponseEntity<UserResDto> updateUser(@Valid @RequestBody UpdateUserDto updateReqDto, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.updateUser(userDetails, updateReqDto));
    }
}
