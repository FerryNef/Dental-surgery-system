package com.ads.dentalapp.service.impl;

import com.ads.dentalapp.exception.PatientNotFoundException;
import com.ads.dentalapp.model.Address;
import com.ads.dentalapp.model.Patient;
import com.ads.dentalapp.repository.AddressRepository;
import com.ads.dentalapp.repository.PatientRepository;
import com.ads.dentalapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    final PatientRepository patientRepository;
    final AddressRepository addressRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    public Patient savePatient(Patient patient) {
        if (patient.getAddress() != null && patient.getAddress().getId() != null) {
            Address fullAddress = addressRepository.findById(patient.getAddress().getId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            patient.setAddress(fullAddress);
        }
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient existing = patientRepository.findById(id)
                        .orElseThrow(() -> new PatientNotFoundException(id));
        existing.setFirstName(updatedPatient.getFirstName());
        existing.setLastName(updatedPatient.getLastName());
        existing.setEmail(updatedPatient.getEmail());
        existing.setPhone(updatedPatient.getPhone());
        existing.setDateOfBirth(updatedPatient.getDateOfBirth());


        return patientRepository.save(existing);
    }

    @Override
    public List<Patient> searchPatients(String searchString) {
        return patientRepository.searchPatients(searchString);
    }

    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        patient.getAppointments().clear();
        patient.getBills().clear();

        patientRepository.delete(patient);
    }

    public List<Patient> getAllPatientsSortedByLastName () {
        return patientRepository.findAllByOrderByLastNameAsc();
    }
}
