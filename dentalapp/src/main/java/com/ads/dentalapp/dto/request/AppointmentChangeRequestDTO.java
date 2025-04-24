package com.ads.dentalapp.dto.request;

public record AppointmentChangeRequestDTO(
        String newDate,
        String newTime
) {
}
