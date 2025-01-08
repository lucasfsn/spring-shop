package com.example.demo.model.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email(message = "Email should be valid")
    private String email;

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

    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "Postal code should be in the format xx-xxx")
    private String postalCode;

    @NotBlank(message = "Country should not be blank")
    @Size(min = 3, message = "Country should have at least 3 characters")
    private String country;

    @OneToOne(mappedBy = "deliveryInfo")
    private Order order;
}
