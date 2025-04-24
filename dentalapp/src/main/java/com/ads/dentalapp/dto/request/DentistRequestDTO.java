package com.ads.dentalapp.dto.request;

import com.ads.dentalapp.model.User;
import lombok.Builder;

@Builder
public record DentistRequestDTO(
        String username,
        String firstName,
        String lastName,
        String contactNumber,
        String email,
        String specialization

) {
}
