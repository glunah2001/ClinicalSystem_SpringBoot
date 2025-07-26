package com.glunah2001.ClinicSystemAPI.model;

import com.glunah2001.ClinicSystemAPI.enums.Specialization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table(
        name = "doctors",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_doctor_MedLic", columnNames = "medicalLicenceNumber")
        })
public class Doctor extends User{
    @Column(unique = true, nullable = false)
    private String medicalLicenceNumber;
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
}
