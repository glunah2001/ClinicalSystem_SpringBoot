package com.glunah2001.ClinicSystemAPI.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterDto(
        @NotBlank(message = "The national identification document number (cédula) is an obligatory data")
        @Pattern(regexp = "^([1-7]\\d{8}|80\\d{7}|\\d{12})$", message = "Please, type your Costa Rican " +
                "identification document number acording to the formats: X0XXX0XXX if you have national id " +
                "or the 12 digits of your DIMEX")
        String cedula,

        @NotBlank(message = "The name is an obligatory data.")
        @Size(max = 20, message = "Please, type only your name.")
        String name,

        @NotBlank(message = "The fist lastname is an obligatory data.")
        @Size(max = 20, message = "Please, type only your first last name.")
        String firstLastname,

        @NotBlank(message = "The second lastname is an obligatory data.")
        @Size(max = 20, message = "Please, type only your second last name.")
        String secondLastname,

        @NotBlank(message = "The username is an obligatory data.")
        @Size(max = 25, message = "Please type a valid username. Between 5 and 50 characters.")
        String username,

        @NotBlank(message = "The email is an obligatory data.")
        @Email(message = "Please, type a valid email.")
        String email,

        @NotBlank(message = "The password is an obligatory data.")
        @Size(min = 12, max = 25, message = "Please type a secure password. It's recommended to be between " +
                "12 and 25 characters.")
        String password
) {
}
