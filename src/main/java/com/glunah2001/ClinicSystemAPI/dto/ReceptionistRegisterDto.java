package com.glunah2001.ClinicSystemAPI.dto;

import com.glunah2001.ClinicSystemAPI.enums.Shift;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record ReceptionistRegisterDto(
        @Valid
        UserRegisterDto userDto,
        @NotBlank(message = "The shift is an obligatory data.")
        Shift shift,
        @NotBlank(message = "The extension number is an obligatory data.")
        String extensionNumber

) {
}
