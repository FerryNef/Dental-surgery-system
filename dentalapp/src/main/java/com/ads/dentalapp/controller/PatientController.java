package com.ads.dentalapp.controller;

import com.ads.dentalapp.dto.request.PatientRequestDTO;
import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.dto.response.PatientResponseDTO;
import com.ads.dentalapp.mapper.AppointmentMapper;
import com.ads.dentalapp.mapper.PatientMapper;
import com.ads.dentalapp.model.Appointment;
import com.ads.dentalapp.model.Patient;
import com.ads.dentalapp.service.AppointmentService;
import com.ads.dentalapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/DentalSurgery/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final PatientMapper patientMapper;
    private final AppointmentMapper appointmentMapper;

    @Autowired
    public PatientController(PatientService patientService, AppointmentService appointmentService, PatientMapper patientMapper, AppointmentMapper appointmentMapper) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.patientMapper = patientMapper;
        this.appointmentMapper = appointmentMapper;
    }

    @GetMapping
    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientService.getAllPatientsSortedByLastName();
        return patientMapper.toDtoList(patients);
    }


    @GetMapping("/appointments")
    public List<AppointmentResponseDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return appointmentMapper.toDtoList(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        Patient patient= patientService.getPatientById(id);
        return ResponseEntity.ok(patientMapper.toDto(patient));
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        Patient savedPatient = patientService.savePatient(patientMapper.toEntity(patientRequestDTO));
        return ResponseEntity.ok(patientMapper.toDto(savedPatient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @RequestBody PatientRequestDTO patientRequestDTO) {
        Patient updatedPatient = patientService.updatePatient(id, patientMapper.toEntity(patientRequestDTO));
        return ResponseEntity.ok(patientMapper.toDto(updatedPatient));
    }

    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<PatientResponseDTO>> searchPatients(@PathVariable String searchString) {
        List<Patient> patients = patientService.searchPatients(searchString);
        return ResponseEntity.ok(patientMapper.toDtoList(patients));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient with the name " + patient.getFirstName() + " " + patient.getLastName() + " was deleted.");
    }
}