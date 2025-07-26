package com.glunah2001.ClinicSystemAPI.service;

import com.glunah2001.ClinicSystemAPI.dto.PatientRegisterDto;
import com.glunah2001.ClinicSystemAPI.model.Patient;

public interface PatientService {
    Patient registerPatient(PatientRegisterDto patientDto);
}
