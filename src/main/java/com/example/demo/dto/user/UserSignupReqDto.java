package com.example.demo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignupReqDto {
    @NotBlank(message = "First name cannot be blank")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "First name should start with an uppercase letter and contain only letters")
    @Size(min = 2, max = 50, message = "First name should be between 2 and 50 characters long")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Last name should start with an uppercase letter and contain only letters")
    @Size(min = 2, max = 50, message = "Last name should be between 2 and 50 characters long")
    private String lastName;
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username should contain only letters and digits")
    @Size(min = 5, max = 25, message = "Username should be between 5 and 25 characters long")
    private String username;
    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password should be between 8 and 50 characters long")
    private String password;
}
