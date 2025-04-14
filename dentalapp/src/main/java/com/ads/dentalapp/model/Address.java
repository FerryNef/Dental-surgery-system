package com.ads.dentalapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String city;

    private String state;

    private String zipCode;

    private String country;

    public Long getId() {
        return id;
    }
}