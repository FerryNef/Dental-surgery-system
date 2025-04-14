package com.ads.dentalapp.repository;

import com.ads.dentalapp.model.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurgeryRepository extends JpaRepository<Surgery, Long> {
}
