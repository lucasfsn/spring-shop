package com.example.demo.service;

import com.example.demo.dto.user.ChangeUserRoleReqDto;
import com.example.demo.dto.user.UpdateUserDto;
import com.example.demo.dto.user.UserResDto;
import com.example.demo.exception.AlreadyExistException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.user.UserMapper;
import com.example.demo.model.user.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthService authService;

    public UserResDto getUser(UUID id) {
        User user = getUserById(id);
        return userMapper.toDto(user);
    }

    public UserResDto changeUserRole(UserDetails userDetails, UUID id, ChangeUserRoleReqDto role) {
        authService.hasAdminAuthority(userDetails);

        User user = getUserById(id);
        user.setRole(role.getRole());
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public UserResDto updateUser(UserDetails userDetails, UpdateUserDto updateUserData) {
        User user = getUserByUsername(userDetails.getUsername());

        if (userRepository.existsByEmailAndIdIsNot(updateUserData.getEmail(), user.getId())) {
            throw new AlreadyExistException("Email already taken");
        }

        user.setFirstName(updateUserData.getFirstName());
        user.setLastName(updateUserData.getLastName());
        user.setEmail(updateUserData.getEmail());
        if (updateUserData.getPassword() != null && !updateUserData.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateUserData.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    private User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}