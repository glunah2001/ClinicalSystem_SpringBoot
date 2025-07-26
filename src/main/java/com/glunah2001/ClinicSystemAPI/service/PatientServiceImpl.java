package com.glunah2001.ClinicSystemAPI.service;

import com.glunah2001.ClinicSystemAPI.dto.PatientRegisterDto;
import com.glunah2001.ClinicSystemAPI.enums.Role;
import com.glunah2001.ClinicSystemAPI.exception.ExceptionUtils;
import com.glunah2001.ClinicSystemAPI.exception.ResourceAlreadyExists;
import com.glunah2001.ClinicSystemAPI.model.Patient;
import com.glunah2001.ClinicSystemAPI.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Patient registerPatient(final PatientRegisterDto patientDto){
        try{
            final Patient patient = new Patient();
            patient.setCedula(patientDto.userDto().cedula());
            patient.setName(patientDto.userDto().name());
            patient.setFirstLastname(patientDto.userDto().firstLastname());
            patient.setSecondLastname(patientDto.userDto().secondLastname());
            patient.setUsername(patientDto.userDto().username());
            patient.setEmail(patientDto.userDto().email());
            patient.setCellphone(patientDto.userDto().cellphone());
            patient.setPassword(passwordEncoder.encode(patientDto.userDto().password()));
            patient.setRole(Role.ROLE_PATIENT);
            patient.setActive(true);
            patient.setDirection(patientDto.direction());
            return patientRepository.save(patient);
        }catch(DataIntegrityViolationException e){
            String message = ExceptionUtils.extractUserConstraintMessage(e, patientDto.userDto());
            throw new ResourceAlreadyExists(message);
        }
    }

}
