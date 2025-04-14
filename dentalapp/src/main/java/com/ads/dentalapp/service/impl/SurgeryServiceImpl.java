package com.ads.dentalapp.service.impl;
import com.ads.dentalapp.model.Surgery;
import com.ads.dentalapp.repository.SurgeryRepository;
import com.ads.dentalapp.service.SurgeryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurgeryServiceImpl implements SurgeryService {

    private final SurgeryRepository surgeryRepository;

    @Override
    public Surgery saveSurgery(Surgery surgery) {
        return surgeryRepository.save(surgery);
    }

    @Override
    public Surgery getSurgeryById(Long id) {
        return surgeryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Surgery not found"));
    }

    @Override
    public List<Surgery> getAllSurgeries() {
        return surgeryRepository.findAll();
    }

    @Override
    public void deleteSurgery(Long id) {
        surgeryRepository.deleteById(id);
    }

    @Override
    public Surgery updateSurgery(Surgery surgery) {
        return surgeryRepository.save(surgery);
    }
}

