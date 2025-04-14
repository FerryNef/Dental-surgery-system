package com.ads.dentalapp.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDTO(
        Long dentistId,
        Long patientId,
        Long surgeryId,
        LocalDate date,
        LocalTime time
) {
}
