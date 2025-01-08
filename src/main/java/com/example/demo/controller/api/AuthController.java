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

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResDto> signUp(@Valid @RequestBody UserSignupReqDto signupRequest) {
        AuthResDto createdUser = authService.signUp(signupRequest);
        return ResponseEntity.created(URI.create("/api/users/" + createdUser.getUser().getId())).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(@Valid @RequestBody UserLoginReqDto loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }
}
