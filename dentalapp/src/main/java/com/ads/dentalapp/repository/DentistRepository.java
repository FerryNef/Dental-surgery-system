package com.ads.dentalapp.repository;

import com.ads.dentalapp.model.Dentist;
import com.ads.dentalapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DentistRepository extends JpaRepository<Dentist, Long> {

    Optional<Dentist> findByUser(User user);
    Optional<Dentist> findByEmail(String email);
}
