package com.ads.dentalapp.dto.request;

import org.hibernate.annotations.processing.Pattern;

public record UserRequestDTO(

        String username,
        String password,
        RoleRequestDTO roleRequestDto
) {
}
