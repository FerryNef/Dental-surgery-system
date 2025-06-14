package com.ads.dentalapp.controller;

import com.ads.dentalapp.dto.request.AppointmentRequestDTO;
import com.ads.dentalapp.dto.request.BillRequestDTO;
import com.ads.dentalapp.dto.request.DentistRequestDTO;
import com.ads.dentalapp.dto.request.PatientRequestDTO;
import com.ads.dentalapp.dto.response.PatientResponseDTO;
import com.ads.dentalapp.mapper.DentistMapper;
import com.ads.dentalapp.mapper.PatientMapper;
import com.ads.dentalapp.model.Patient;
import com.ads.dentalapp.repository.DentistRepository;
import com.ads.dentalapp.service.AppointmentService;
import com.ads.dentalapp.service.BillService;
import com.ads.dentalapp.service.DentistService;
import com.ads.dentalapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/manager")
@PreAuthorize("hasRole('OFFICE_MANAGER')")
public class OfficeManagerController {
    @Autowired
    private DentistService dentistService;
    @Autowired
    private DentistMapper dentistMapper;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private BillService billService;

    @GetMapping
    public String getManagerDashboard() {
        return "Welcome, Office Manager!";
    }

    @PostMapping("/Dentist")
    public ResponseEntity<String> registerDentist (@RequestBody DentistRequestDTO dentistRequestDTO) {
        dentistService.saveDentist(dentistRequestDTO);
        return ResponseEntity.ok("Dentist registered successfully");

    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        Patient patient= patientService.getPatientById(id);
        return ResponseEntity.ok(patientMapper.toDto(patient));
    }
    @GetMapping("/patients")
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Page<Patient> patients = patientService.getAllPatients(page, size);
        Page<PatientResponseDTO> patientResponseDTOs = patientMapper.toDtoPage(patients);
        return ResponseEntity.ok(patientResponseDTOs);
    }

    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<PatientResponseDTO>> searchPatients(@PathVariable String searchString) {
        List<Patient> patients = patientService.searchPatients(searchString);
        return ResponseEntity.ok(patientMapper.toDtoList(patients));
    }

    @DeleteMapping("Patient/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient with the name " + patient.getFirstName() + " " + patient.getLastName() + " was deleted.");
    }

//    @PostMapping("appointments")
//    public ResponseEntity<String> scheduleAppointment (@RequestBody AppointmentRequestDTO appointmentRequestDTO) {
//        appointmentService.scheduleAppointment(appointmentRequestDTO);
//        return ResponseEntity.ok("appointment scheduled");
//    }

    @PutMapping("/appointments/{id}/accept-change")
    public ResponseEntity<String> acceptAppointmentChange(@PathVariable Long id) {
        appointmentService.acceptAppointmentChange(id);  // Call service to accept the change
        return ResponseEntity.ok("Appointment change request accepted.");
    }

    @PutMapping("/appointments/{id}/accept-cancel")
    public ResponseEntity<String> acceptAppointmentCancellation(@PathVariable Long id) {
        appointmentService.acceptAppointmentCancellation(id);  // Call service to accept the cancellation
        return ResponseEntity.ok("Appointment cancellation request accepted.");
    }

    @PutMapping("appointment/{id}")
    public ResponseEntity<String> updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        appointmentService.updateAppointment(id, appointmentRequestDTO);
        return ResponseEntity.ok("Appointment updated");
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok("Appointment canceled.");
    }

    @PutMapping("/bills/{billId}")
    public ResponseEntity<String> updatePatientBill(
            @PathVariable Long billId,
            @RequestBody BillRequestDTO billRequestDTO) {
        billService.updateBill(billId, billRequestDTO);
        return ResponseEntity.ok("Bill updated successfully.");
    }

    @PostMapping("/patients/{id}/bills")
    public ResponseEntity<String> addBillToPatient(@PathVariable Long id, @RequestBody BillRequestDTO billRequestDTO) {
        // Call the service to create the bill
        billService.addBillToPatient(id, billRequestDTO);
        return ResponseEntity.ok("Bill added successfully to patient with ID " + id);
    }


}
