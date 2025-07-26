package com.glunah2001.ClinicSystemAPI.controller;

import com.glunah2001.ClinicSystemAPI.dto.PatientRegisterDto;
import com.glunah2001.ClinicSystemAPI.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody final PatientRegisterDto patientDto){
        final var user = patientService.registerPatient(patientDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/patient/{username}")
                .buildAndExpand(user.getUsername())
                .toUri();
        return ResponseEntity.created(location).body("Patient registered successfully");
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Todo en orden capo");
    }

    @GetMapping("/test2")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> test2(){
        return ResponseEntity.ok("Todo en orden capo2");
    }
}
