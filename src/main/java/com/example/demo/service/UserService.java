package com.example.demo.service;

import com.example.demo.dto.user.UpdateUserReqDto;
import com.example.demo.dto.user.UserResDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.user.UserMapper;
import com.example.demo.model.user.User;
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

    public UserResDto updateUser(UUID id, UpdateUserReqDto updateUserData) {
        User user = getUserById(id);

        if (updateUserData.getFirstName() != null) {
            user.setFirstName(updateUserData.getFirstName());
        }
        if (updateUserData.getLastName() != null) {
            user.setLastName(updateUserData.getLastName());
        }
        if (updateUserData.getEmail() != null) {
            if (checkIfEmailExists(updateUserData.getEmail(), id)) {
                throw new BadCredentialsException("Email already taken");
            }
            user.setEmail(updateUserData.getEmail());
        }
        if (updateUserData.getUsername() != null) {
            if (checkIfUsernameExists(updateUserData.getUsername(), id)) {
                throw new BadCredentialsException("Username already taken");
            }
            user.setUsername(updateUserData.getUsername());
        }
        if (updateUserData.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserData.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private boolean checkIfUsernameExists(String username, UUID currentUserId) {
        return userRepository.findByUsername(username)
                .filter(user -> !user.getId().equals(currentUserId))
                .isPresent();
    }

    private boolean checkIfEmailExists(String email, UUID currentUserId) {
        return userRepository.findByEmail(email)
                .filter(user -> !user.getId().equals(currentUserId))
                .isPresent();
    }


}