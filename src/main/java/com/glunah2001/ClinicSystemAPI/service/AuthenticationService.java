package com.glunah2001.ClinicSystemAPI.service;

import com.glunah2001.ClinicSystemAPI.dto.UserLoginDto;
import com.glunah2001.ClinicSystemAPI.dto.token.TokenResponse;
import jakarta.validation.Valid;

public interface AuthenticationService {
    TokenResponse login(UserLoginDto userDto);
}
