package com.example.demo.mapper.user;

import com.example.demo.dto.user.SearchUserResDto;
import com.example.demo.dto.user.UserResDto;
import com.example.demo.dto.user.UserSignupReqDto;
import com.example.demo.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserResDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public SearchUserResDto toSearchDto(User user) {
        if (user == null) {
            return null;
        }

        return SearchUserResDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
    }

    public User toEntity(UserSignupReqDto userReqDto) {
        if (userReqDto == null) {
            return null;
        }

        return User.builder()
                .firstName(userReqDto.getFirstName())
                .lastName(userReqDto.getLastName())
                .username(userReqDto.getUsername())
                .email(userReqDto.getEmail())
                .build();
    }
}
