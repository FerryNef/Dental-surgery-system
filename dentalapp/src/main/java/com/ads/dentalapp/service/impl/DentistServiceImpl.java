package com.ads.dentalapp.service.impl;
import com.ads.dentalapp.dto.request.DentistRequestDTO;
import com.ads.dentalapp.mapper.DentistMapper;
import com.ads.dentalapp.model.Dentist;
import com.ads.dentalapp.model.User;
import com.ads.dentalapp.repository.DentistRepository;
import com.ads.dentalapp.repository.UserRepository;
import com.ads.dentalapp.service.DentistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DentistServiceImpl implements DentistService {

    private final DentistRepository dentistRepository;
    private UserRepository userRepository;
    private DentistMapper dentistMapper;
    @Override
    public Page<Dentist> getPaginatedDentists(Pageable pageable) {
        return dentistRepository.findAll(pageable);
    }


    @Override
    public Dentist saveDentist(DentistRequestDTO dentistDTO) {

        if (dentistRepository.findByEmail(dentistDTO.email()).isPresent()) {
            throw new RuntimeException("Dentist already exists with email: " + dentistDTO.email());
        }
        Dentist dentist =dentistMapper.toEntity(dentistDTO);

        User user = userRepository.findByUsername(dentistDTO.username())
                .orElseThrow(()-> new RuntimeException("user not found"));
        dentist.setUser(user);

        return dentistRepository.save(dentist);
    }

    @Override
    public Dentist getDentistById(Long id) {
        return dentistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dentist not found"));
    }

    @Override
    public List<Dentist> getAllDentists() {
        return dentistRepository.findAll();
    }

    @Override
    public void deleteDentist(Long id) {
        dentistRepository.deleteById(id);
    }

    @Override
    public Dentist updateDentist(Dentist dentist) {
        return dentistRepository.save(dentist);
    }
}
