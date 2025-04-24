package com.ads.dentalapp.dto.request;

import java.time.LocalDate;

public record PatientRequestDTO(
        String username,

        String firstName,
        String lastName,
        String phone,
        String email,
        LocalDate dateOfBirth,
        Long addressId
) {
}
