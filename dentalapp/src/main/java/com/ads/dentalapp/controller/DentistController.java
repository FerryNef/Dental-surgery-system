package com.ads.dentalapp.controller;

import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.dto.response.PatientResponseDTO;
import com.ads.dentalapp.mapper.PatientMapper;
import com.ads.dentalapp.model.Patient;
import com.ads.dentalapp.service.AppointmentService;
import com.ads.dentalapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dentists")
@PreAuthorize("hasRole('DENTIST')")
public class DentistController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private PatientService patientService;

    @GetMapping
    public String DentistDashboard () {
        return "Welcome Dentist";
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getDentistAppointments() {
        return ResponseEntity.ok(appointmentService.getAppointmentForLoggedDentist());
    }

    @GetMapping("/appointments/{id}/patient")
    public ResponseEntity<PatientResponseDTO> getPatientInfo (@PathVariable Long id) {
        PatientResponseDTO patient = appointmentService.getPatientForAppointment(id);
        return  ResponseEntity.ok(patient);
    }

    @GetMapping("patient/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        Patient patient= patientService.getPatientById(id);
        return ResponseEntity.ok(patientMapper.toDto(patient));
    }
}
