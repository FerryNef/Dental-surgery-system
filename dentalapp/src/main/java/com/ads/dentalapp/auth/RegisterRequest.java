package com.ads.dentalapp.auth;

import com.ads.dentalapp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;



public record RegisterRequest(

        String firstName,
        String lastName,
        String email,
        String password,
        Role role
) {
}
