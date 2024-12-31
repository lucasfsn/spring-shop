package com.example.demo.controller.api;

import com.example.demo.dto.user.ChangeUserRoleReqDto;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ApiUserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResDto> getUser(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResDto> changeUserRole(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ChangeUserRoleReqDto role) {
        return ResponseEntity.ok(userService.changeUserRole(id, role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResDto> updateUser(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UpdateUserReqDto updateReqDto) {
        return ResponseEntity.ok(userService.updateUser(id, updateReqDto));
    }
}
