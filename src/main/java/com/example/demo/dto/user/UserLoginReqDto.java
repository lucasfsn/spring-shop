package com.example.demo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginReqDto {
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username should contain only letters and digits")
    @Size(min = 5, max = 25, message = "Username should be between 5 and 25 characters long")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password should be between 8 and 50 characters long")
    private String password;
}
