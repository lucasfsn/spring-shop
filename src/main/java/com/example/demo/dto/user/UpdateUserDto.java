package com.example.demo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UpdateUserDto {
    @Pattern(regexp = "^[a-zA-Z\\xC0-\\uFFFF]+([ \\-']?[a-zA-Z\\xC0-\\uFFFF]+){0,2}[.]?$", message = "First name cannot contain numbers and special characters")
    @Size(min = 1, max = 50, message = "First name should be between 1 and 50 characters long")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z\\xC0-\\uFFFF]+([ \\-']?[a-zA-Z\\xC0-\\uFFFF]+){0,2}[.]?$", message = "Last name cannot contain numbers and special characters")
    @Size(min = 1, max = 50, message = "Last name should be between 1 and 50 characters long")
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    private String password;
}
