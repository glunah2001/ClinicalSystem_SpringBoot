package com.glunah2001.ClinicSystemAPI.controller;

import com.glunah2001.ClinicSystemAPI.dto.UserLoginDto;
import com.glunah2001.ClinicSystemAPI.dto.token.TokenResponse;
import com.glunah2001.ClinicSystemAPI.service.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationServiceImpl authService;

    public ResponseEntity<TokenResponse> login(@Valid @RequestBody final UserLoginDto userDto){
        TokenResponse token = authService.login(userDto);
        return ResponseEntity.ok(token);
    }
}
