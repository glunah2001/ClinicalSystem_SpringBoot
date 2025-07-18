package com.glunah2001.ClinicSystemAPI.service;

import com.glunah2001.ClinicSystemAPI.dto.UserRegisterDto;
import com.glunah2001.ClinicSystemAPI.model.User;

public interface PatientService {
    User registerPatient(UserRegisterDto userDto);
}
