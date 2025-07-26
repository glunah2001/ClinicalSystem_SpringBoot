package com.glunah2001.ClinicSystemAPI.dto;

import com.glunah2001.ClinicSystemAPI.enums.Shift;

public record ReceptionistDto(
        String name,
        String firstLastname,
        String secondLastname,
        Shift shift,
        String extensionNumber
) {
}
