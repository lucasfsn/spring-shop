package com.example.demo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupReqDto {
    @Pattern(regexp = "^[a-zA-Z\\xC0-\\uFFFF]+([ \\-']?[a-zA-Z\\xC0-\\uFFFF]+){0,2}[.]?$", message = "First name cannot contain numbers and special characters")
    @Size(min = 1, max = 50, message = "First name should be between 1 and 50 characters long")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z\\xC0-\\uFFFF]+([ \\-']?[a-zA-Z\\xC0-\\uFFFF]+){0,2}[.]?$", message = "Last name cannot contain numbers and special characters")
    @Size(min = 1, max = 50, message = "Last name should be between 1 and 50 characters long")
    private String lastName;
    @Pattern(regexp = "^[a-zA-Z\\xC0-\\uFFFF]+([ \\-']?[a-zA-Z\\xC0-\\uFFFF]+){0,2}[.]?$", message = "Username cannot contain numbers and special characters")
    @Size(min = 1, max = 25, message = "Username should be between 1 and 25 characters long")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^\\S{8,}$", message = "Password should be at least 8 characters long and cannot contain any whitespace characters")
    private String password;
}
