package com.ads.dentalapp.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
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
        List<Patient> patients = patientService.getAllPatients();
        return patientMapper.toDtoList(patients);
    }


    @GetMapping("/appointments")
    public List<AppointmentResponseDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return appointmentMapper.toDtoList(appointments);
    }

    @GetMapping("/{id}")
    public Patient getById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PostMapping
    public Patient create(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable Long id, @RequestBody Patient patient) {
        return patientService.updatePatient(id, patient);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}