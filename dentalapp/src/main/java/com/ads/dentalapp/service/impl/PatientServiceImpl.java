package com.ads.dentalapp.service.impl;

import com.ads.dentalapp.dto.request.PatientRequestDTO;
import com.ads.dentalapp.exception.PatientNotFoundException;
import com.ads.dentalapp.model.Address;
import com.ads.dentalapp.model.Bill;
import com.ads.dentalapp.model.Patient;
import com.ads.dentalapp.repository.AddressRepository;
import com.ads.dentalapp.repository.PatientRepository;
import com.ads.dentalapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    final PatientRepository patientRepository;
    final AddressRepository addressRepository;

    @Override
    public Page<Patient> getAllPatients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findAll(pageable);
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    public Patient savePatient(PatientRequestDTO patientDTO) {
        if (patientRepository.findByEmail(patientDTO.email()).isPresent()) {
            throw new RuntimeException("Patient already exists with email: " + patientDTO.email());
        }
        Patient patient = new Patient();
        patient.setFirstName(patientDTO.firstName());
        patient.setLastName(patientDTO.lastName());
        patient.setPhone(patientDTO.phone());
        patient.setEmail(patientDTO.email());
        patient.setDateOfBirth(patientDTO.dateOfBirth());


        if (patientDTO.addressId() != null) {
            Address address = addressRepository.findById(patientDTO.addressId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            patient.setAddress(address);
        }


//        Bill bill = new Bill();
//        bill.setAmount(amount);
//        bill.setStatus(status);
//        bill.setBillDate(LocalDate.now());
//        bill.setPatient(patient);

      //  patient.setBills(List.of(bill));

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
