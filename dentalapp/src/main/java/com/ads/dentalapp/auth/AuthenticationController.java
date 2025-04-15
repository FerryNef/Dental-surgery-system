package com.ads.dentalapp.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @RequestMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        return null;
    }

    @RequestMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody RegisterRequest registerRequest) {
        return null;
    }


}
