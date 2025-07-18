package com.glunah2001.ClinicSystemAPI.dto;

import jakarta.validation.constraints.Size;

public record UserLoginDto(
        @Size(min = 5, max = 50, message = "Please type your username. It must be between 5 and 50 characters.")
        String username,
        @Size(min = 12, max = 25, message = "Please type your password.")
        String password
) {
}
