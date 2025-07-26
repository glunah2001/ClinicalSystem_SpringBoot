package com.glunah2001.ClinicSystemAPI.model;

import com.glunah2001.ClinicSystemAPI.enums.Shift;
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
@Table(name = "receptionists")
public class Receptionist extends User{
    @Enumerated(EnumType.STRING)
    private Shift shift;
    @Column(nullable = false)
    private String extensionNumber;
}
