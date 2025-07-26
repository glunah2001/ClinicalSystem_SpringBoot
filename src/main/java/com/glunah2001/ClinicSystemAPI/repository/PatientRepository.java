package com.glunah2001.ClinicSystemAPI.repository;

import com.glunah2001.ClinicSystemAPI.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
