package com.example.demo.model.user;

import com.example.demo.model.cart.Cart;
import com.example.demo.model.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
