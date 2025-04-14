package com.ads.dentalapp.dto.response;

import java.time.LocalDate;
import java.util.List;


public record PatientResponseDTO(
        String firstName,
        String lastName,
        String phone,
        String email,
        LocalDate dateOfBirth,
        Long addressId,
        List<AppointmentResponseDTO> appointments
) {
}
