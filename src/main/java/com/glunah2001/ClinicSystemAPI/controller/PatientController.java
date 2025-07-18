package com.glunah2001.ClinicSystemAPI.controller;

import com.glunah2001.ClinicSystemAPI.dto.UserRegisterDto;
import com.glunah2001.ClinicSystemAPI.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody final UserRegisterDto userDTO){
        final var user = patientService.registerPatient(userDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(user.getUsername())
                .toUri();
        return ResponseEntity.created(location).body("Patient registered successfully");
    }
}
