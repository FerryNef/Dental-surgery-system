package com.ads.dentalapp.dto.response;

public record DentistResponseDTO(
         Long id,
         String firstName,
         String lastName,
         String email,
         String specialization
) {
}
