package com.example.demo.dto.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class DeliveryInfoDto {
    private UUID id;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be blank")
    private String email;
    @NotBlank(message = "Phone number should not be blank")
    @Size(min = 9, max = 9, message = "Phone number should have 9 digits")
    private String phoneNumber;
    @NotBlank(message = "Street should not be blank")
    @Size(min = 3, message = "Street should have at least 3 characters")
    private String street;
    @NotBlank(message = "House number should not be blank")
    private String houseNumber;
    @NotBlank(message = "Flat number should not be blank")
    private String flatNumber;
    @NotBlank(message = "City should not be blank")
    @Size(min = 3, message = "City should have at least 3 characters")
    private String city;
    @NotBlank(message = "Postal code should not be blank")
    @Size(min = 6, max = 6, message = "Postal code should have 6 characters")
    private String postalCode;
    @NotBlank(message = "Country should not be blank")
    @Size(min = 3, message = "Country should have at least 3 characters")
    private String country;
}
