package com.glunah2001.ClinicSystemAPI.dto;

import com.glunah2001.ClinicSystemAPI.enums.Specialization;

public record DoctorDto(
        String name,
        String firstLastname,
        String secondLastname,
        String email,
        String cellphone,
        Specialization specialization
) {
}
