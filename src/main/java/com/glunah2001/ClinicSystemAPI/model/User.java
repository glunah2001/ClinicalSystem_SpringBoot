package com.glunah2001.ClinicSystemAPI.model;

import com.glunah2001.ClinicSystemAPI.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_user_cedula", columnNames = "cedula"),
                @UniqueConstraint(name = "UK_user_username", columnNames = "username"),
                @UniqueConstraint(name = "UK_user_email", columnNames = "email")
        })
public class User {

    @Version
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 12)
    private String cedula;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String firstLastname;
    @Column(nullable = false, length = 20)
    private String secondLastname;
    @Column(nullable = false, unique = true, length = 25)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean active;
}
