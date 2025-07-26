package com.glunah2001.ClinicSystemAPI.model;

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
@Table(name = "receptionists")
public class Receptionist {
    private String direction;
    private String cellphone;
    //private User user;
}
