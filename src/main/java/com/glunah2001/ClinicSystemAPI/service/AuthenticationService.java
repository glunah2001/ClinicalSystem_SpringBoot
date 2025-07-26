package com.glunah2001.ClinicSystemAPI.service;

import com.glunah2001.ClinicSystemAPI.dto.UserLoginDto;
import com.glunah2001.ClinicSystemAPI.dto.token.TokenResponse;

public interface AuthenticationService {
    TokenResponse login(UserLoginDto userDto);
    TokenResponse refresh(String authHeader);
}
