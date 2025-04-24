package com.ads.dentalapp.service;
import com.ads.dentalapp.dto.request.DentistRequestDTO;
import com.ads.dentalapp.model.Dentist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DentistService {
    Dentist saveDentist(DentistRequestDTO dentistDTO);
    Dentist getDentistById(Long id);
    List<Dentist> getAllDentists();
    void deleteDentist(Long id);
    Dentist updateDentist(Dentist dentist);
    Page<Dentist> getPaginatedDentists(Pageable pageable);
}