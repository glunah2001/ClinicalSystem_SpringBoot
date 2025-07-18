package com.glunah2001.ClinicSystemAPI.exception;

import com.glunah2001.ClinicSystemAPI.dto.UserRegisterDto;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;

public class ExceptionUtils {

    public static String extractConstraintMessage(DataIntegrityViolationException e, UserRegisterDto userDto) {
        Throwable rootCause = e.getRootCause();
        if (rootCause instanceof SQLIntegrityConstraintViolationException sqlEx) {
            String message = sqlEx.getMessage();
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
        }
        return "Restriction violation on registration attempt: " + e.getMessage();
    }
}
