package com.glunah2001.ClinicSystemAPI.exception;

import com.glunah2001.ClinicSystemAPI.dto.DoctorRegisterDto;
import com.glunah2001.ClinicSystemAPI.dto.UserRegisterDto;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;

public class ExceptionUtils {

    public static String extractUserConstraintMessage(DataIntegrityViolationException e, UserRegisterDto userDto) {
        String message = getExceptionMessage(e);
        if (message != null) {
            if (message.contains("UK_user_cedula")) {
                return "This National Identification Document (cédula) number is already registered: "
                        + userDto.cedula();
            } else if (message.contains("UK_user_email")) {
                return "This Email is already registered: "
                        + userDto.email();
            } else if (message.contains("UK_user_username")) {
                return "This username is already registered: "
                        + userDto.username();
            }
        }
        return "Restriction violation on registration attempt: " + e.getMessage();
    }

    public static String extractDoctorConstraintMessage(DataIntegrityViolationException e,
                                                        DoctorRegisterDto doctorDto) {
        String message = getExceptionMessage(e);
        if (message != null) {
            if (message.contains("UK_doctor_MedLic")) {
                return "This Medical License number is already registered: "
                        + doctorDto.medicalLicenseNumber();
            }
        }
        return "Restriction violation on registration attempt: " + e.getMessage();
    }

    private static String getExceptionMessage(DataIntegrityViolationException e){
        Throwable rootCause = e.getRootCause();
        if (rootCause instanceof SQLIntegrityConstraintViolationException sqlEx)
            return sqlEx.getMessage();
        return null;
    }
}
