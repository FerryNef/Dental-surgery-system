package com.ads.dentalapp.dto.response;

import java.util.List;

public record AddressResponseDTO(
         Long id,
        String street,
         String city,
        String zipCode,
        List<PatientResponseDTO> patients
) {
}
