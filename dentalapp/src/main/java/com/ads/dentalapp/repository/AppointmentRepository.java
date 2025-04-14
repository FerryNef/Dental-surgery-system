package com.ads.dentalapp.repository;

import com.ads.dentalapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDentistId(Long dentistId);
}
