package com.ads.dentalapp.auth;

import com.ads.dentalapp.model.Role;

public record RegisterRequest(

        String firstName,
        String lastNAme,
        String usrname,
        String password,
        Role role
) {
}
