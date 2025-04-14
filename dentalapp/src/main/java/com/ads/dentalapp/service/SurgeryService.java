package com.ads.dentalapp.service;
import com.ads.dentalapp.model.Surgery;
import java.util.List;

public interface SurgeryService {
    Surgery saveSurgery(Surgery surgery);
    Surgery getSurgeryById(Long id);
    List<Surgery> getAllSurgeries();
    void deleteSurgery(Long id);
    Surgery updateSurgery(Surgery surgery);
}
