package com.ads.dentalapp.service;
import com.ads.dentalapp.model.Dentist;
import java.util.List;

public interface DentistService {
    Dentist saveDentist(Dentist dentist);
    Dentist getDentistById(Long id);
    List<Dentist> getAllDentists();
    void deleteDentist(Long id);
    Dentist updateDentist(Dentist dentist);
}