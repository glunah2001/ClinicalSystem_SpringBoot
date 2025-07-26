package com.glunah2001.ClinicSystemAPI.model;

import com.glunah2001.ClinicSystemAPI.enums.Specialization;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "doctors")
public class Doctor {
    private String medicalLicenceNumber;
    private String cellphone;
    private Specialization specialization;
    //private User user;
}
