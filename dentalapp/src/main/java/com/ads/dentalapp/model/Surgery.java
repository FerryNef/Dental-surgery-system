package com.ads.dentalapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "surgery")
public class Surgery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    private String phone;

    @OneToMany(mappedBy = "surgery", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();


    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}