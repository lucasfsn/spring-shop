package com.example.demo.controller.api;

import com.example.demo.dto.user.AuthResDto;
import com.example.demo.dto.user.UserLoginReqDto;
import com.example.demo.dto.user.UserSignupReqDto;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResDto> signUp(@Valid @RequestBody UserSignupReqDto signupRequest) {
        return ResponseEntity.status(201).body(authService.signUp(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(@Valid @RequestBody UserLoginReqDto loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }
}
