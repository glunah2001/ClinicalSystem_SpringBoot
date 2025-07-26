package com.glunah2001.ClinicSystemAPI.dto;

import com.glunah2001.ClinicSystemAPI.enums.Specialization;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DoctorRegisterDto(
        @Valid
        UserRegisterDto userDto,

        @NotBlank(message = "The medical license number is an obligatory data.")
        @Pattern(regexp = "MED\\d{1,5}", message = "The medical license number be in the format: " +
                "MED followed by 1 to 5 numbers (MED0000)")
        String medicalLicenseNumber,

        @NotBlank(message = "The Specialization is an obligatory data.")
        Specialization specialization
) {
}
