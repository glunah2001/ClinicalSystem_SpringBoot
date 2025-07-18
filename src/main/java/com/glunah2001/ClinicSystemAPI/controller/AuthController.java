package com.glunah2001.ClinicSystemAPI.controller;

import com.glunah2001.ClinicSystemAPI.dto.UserLoginDto;
import com.glunah2001.ClinicSystemAPI.dto.token.TokenResponse;
import com.glunah2001.ClinicSystemAPI.service.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody final UserLoginDto userDto){
        final TokenResponse token = authService.login(userDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestHeader (HttpHeaders.AUTHORIZATION) final String authHeader){
        return authService.refresh(authHeader);
    }
}
