package com.glunah2001.ClinicSystemAPI.dto;

public record PatientDto(
        String name,
        String firstLastname,
        String secondLastname,
        String email,
        String cellphone,
        String direction
) {
}
