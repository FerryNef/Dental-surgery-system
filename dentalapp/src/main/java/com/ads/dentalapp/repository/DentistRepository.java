package com.ads.dentalapp.repository;

import com.ads.dentalapp.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistRepository extends JpaRepository<Dentist, Long> {
}
