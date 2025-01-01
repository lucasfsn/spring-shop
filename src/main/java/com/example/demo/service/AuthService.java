package com.example.demo.service;

import com.example.demo.dto.user.AuthResDto;
import com.example.demo.dto.user.UserSignupReqDto;
import com.example.demo.dto.user.UserLoginReqDto;
import com.example.demo.exception.AlreadyExistException;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.user.UserMapper;
import com.example.demo.model.cart.Cart;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserRole;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResDto signUp(UserSignupReqDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException("Email already taken");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistException("Username already taken");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.CUSTOMER);

        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return new AuthResDto(token, userMapper.toDto(savedUser));
    }

    public AuthResDto authenticate(UserLoginReqDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidDataException("Invalid username or password");
        }

        String token = jwtService.generateToken(user);

        return new AuthResDto(token, userMapper.toDto(user));
    }
}
