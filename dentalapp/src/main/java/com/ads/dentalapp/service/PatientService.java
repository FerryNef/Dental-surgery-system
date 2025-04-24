package com.ads.dentalapp.service;

import com.ads.dentalapp.dto.request.PatientRequestDTO;
import com.ads.dentalapp.model.Patient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {

    Page<Patient> getAllPatients(int page, int size);
    Patient getPatientById(Long id);
    Patient savePatient(PatientRequestDTO patientDTO);
    Patient updatePatient(Long id, Patient patient);
    void deletePatient(Long id);

    List<Patient> getAllPatientsSortedByLastName();

    List<Patient> searchPatients(String searchString);

}
