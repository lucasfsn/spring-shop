package com.example.demo.mapper.user;

import com.example.demo.dto.user.UserSignupReqDto;
import com.example.demo.dto.user.UserResDto;
import com.example.demo.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserResDto userDto = new UserResDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());

        return userDto;
    }

    public User toEntity(UserSignupReqDto userReqDto) {
        if (userReqDto == null) {
            return null;
        }

        User user = new User();
        user.setFirstName(userReqDto.getFirstName());
        user.setLastName(userReqDto.getLastName());
        user.setUsername(userReqDto.getUsername());
        user.setEmail(userReqDto.getEmail());

        return user;
    }
}
