package com.glunah2001.ClinicSystemAPI.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PatientRegisterDto(
        @Valid
        UserRegisterDto userDto,

        @NotBlank(message = "Your direction is an obligatory data.")
        @Size(max = 150, message = "Your direction is too large.")
        String direction
) {
}
