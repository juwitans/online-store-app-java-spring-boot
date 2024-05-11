package com.majumundurcompany.onlineclothingmarketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "username is required")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "invalid username")
    @Size(min = 8, max = 20, message = "username must be greater than 8 characters and less than 20")
    private String username;
    @NotBlank(message = "password is requires")
    @Size(min = 8, message = "password must be greater than 8 characters")
    private String password;
}
