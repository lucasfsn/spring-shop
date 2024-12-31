package com.example.demo.service;

import com.example.demo.dto.user.ChangeUserRoleReqDto;
import com.example.demo.dto.user.UpdateUserReqDto;
import com.example.demo.dto.user.UserResDto;
import com.example.demo.exception.AlreadyExistException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.user.UserMapper;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserRole;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResDto getUser(UUID id) {
        User user = getUserById(id);
        return userMapper.toDto(user);
    }

    public UserResDto changeUserRole(UUID id, ChangeUserRoleReqDto role) {
        User user = getUserById(id);
        user.setRole(role.getRole());
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public UserResDto updateUser(UUID id, UpdateUserReqDto updateUserData) {
        User user = getUserById(id);

        if (userRepository.existsByEmailAndIdIsNot(updateUserData.getEmail(), id)) {
            throw new AlreadyExistException("Email already taken");
        }

        if (userRepository.existsByUsernameAndIdIsNot(updateUserData.getUsername(), id)) {
            throw new AlreadyExistException("Username already taken");
        }

        user.setFirstName(updateUserData.getFirstName());
        user.setLastName(updateUserData.getLastName());
        user.setEmail(updateUserData.getEmail());
        user.setUsername(updateUserData.getUsername());
        if (updateUserData.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserData.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    private User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}