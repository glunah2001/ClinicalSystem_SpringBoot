package com.glunah2001.ClinicSystemAPI.service;

import com.glunah2001.ClinicSystemAPI.dto.UserLoginDto;
import com.glunah2001.ClinicSystemAPI.dto.token.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    @Override
    public TokenResponse login(final UserLoginDto userDto) {
        return null;
    }
}
