package com.ads.dentalapp.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDTO(

        Long id,
        LocalDate date,
        LocalTime time,
        String status,
        boolean isApproved,
        String dentistName,
        String surgeryName,

       String patientName
) {
}
