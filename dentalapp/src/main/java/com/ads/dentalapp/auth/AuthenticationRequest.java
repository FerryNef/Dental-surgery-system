package com.ads.dentalapp.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
