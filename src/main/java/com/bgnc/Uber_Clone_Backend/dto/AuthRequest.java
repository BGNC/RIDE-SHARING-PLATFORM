package com.bgnc.Uber_Clone_Backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequest extends DtoBase {

    @NotNull(message = "The username or email is required field.")
    private String username;

    @NotNull(message = "The password field is required")
    private String password;

    @NotNull(message = "The role field is required")
    private String role;
}
