package com.ads.dentalapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    PATIENT_READ("admin:read"),
    PATIENT_WRITE("admin:write"),
    DENTIST_READ("member:read"),
    DENTIST_WRITE("member:write"),
    OFFICE_MANAGER_READ("member:read"),
    OFFICE_MANAGER_WRITE("member:write");


    @Getter
    private final String permission;
}
