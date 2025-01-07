package com.example.demo.dto.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeliveryInfoDto {
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Phone number should not be blank")
    @Pattern(regexp = "^[0-9]{9}$", message = "Phone number should have 9 digits")
    private String phoneNumber;
    @NotBlank(message = "Street should not be blank")
    @Size(min = 3, message = "Street should have at least 3 characters")
    private String street;
    @NotBlank(message = "House number should not be blank")
    @Size(min = 1, message = "House number should have at least 1 character")
    private String houseNumber;
    @Pattern(regexp = "\\S.*", message = "Flat number should start with a non-whitespace character and have at least 1 character")
    private String flatNumber;
    @NotBlank(message = "City should not be blank")
    @Size(min = 3, message = "City should have at least 3 characters")
    private String city;
    @NotBlank(message = "Postal code should not be blank")
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "Postal code should be in the format xx-xxx")
    private String postalCode;
    @NotBlank(message = "Country should not be blank")
    @Size(min = 3, message = "Country should have at least 3 characters")
    private String country;
}
