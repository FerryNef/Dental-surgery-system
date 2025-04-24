package com.ads.dentalapp.auth;

import lombok.AllArgsConstructor;
import lombok.Data;


public record AuthenticationRequest(
        String username,
        String password
) {
}
