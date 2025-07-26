package com.glunah2001.ClinicSystemAPI.model;

import com.glunah2001.ClinicSystemAPI.enums.Shift;
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
@Table(name = "patients")
public class Patient {
    private Shift shift;
    private String extensionNumber;
    private String personalCellphone;
    //private User user;
}
