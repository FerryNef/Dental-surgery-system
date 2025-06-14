package com.ads.dentalapp.auth;

import com.ads.dentalapp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


public record RegisterRequest(

        String firstName,
        String lastName,
        String email,
        String password,
        Role role,
        String phone,
        LocalDate dateOfBirth,
        String street,
        String city,
        String state,
        String zipCode,
        String country
) {
}
