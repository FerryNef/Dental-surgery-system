package com.ads.dentalapp.exception;

public class PatientNotFoundException extends RuntimeException{

    public PatientNotFoundException (Long id) {
        super("Patient with ID " + id + " not found");
    }
}
