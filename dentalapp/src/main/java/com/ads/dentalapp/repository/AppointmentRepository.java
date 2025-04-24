package com.ads.dentalapp.repository;

import com.ads.dentalapp.model.Appointment;
import com.ads.dentalapp.model.Dentist;
import com.ads.dentalapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDentistId(Long dentistId);
    List<Appointment> findByDentist(Dentist dentist);

    List<Appointment> findByPatient(Patient patient);

    boolean existsByDentistIdAndDateAndTime (Long dentistId, LocalDate date, LocalTime time);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.dentist.id = :dentistId AND a.date BETWEEN :startDate AND :endDate")
    long countAppointmentsByDentistAndWeek(@Param("dentistId") Long dentistId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
