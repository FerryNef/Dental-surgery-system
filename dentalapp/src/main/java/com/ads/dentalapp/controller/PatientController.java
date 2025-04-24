package com.ads.dentalapp.controller;

import com.ads.dentalapp.dto.request.AppointmentCancellationRequestDTO;
import com.ads.dentalapp.dto.request.AppointmentChangeRequestDTO;
import com.ads.dentalapp.dto.request.AppointmentRequestDTO;
import com.ads.dentalapp.dto.request.PatientRequestDTO;
import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.dto.response.BillResponseDTO;
import com.ads.dentalapp.dto.response.DentistResponseDTO;
import com.ads.dentalapp.dto.response.PatientResponseDTO;
import com.ads.dentalapp.mapper.AppointmentMapper;
import com.ads.dentalapp.mapper.BillMapper;
import com.ads.dentalapp.mapper.DentistMapper;
import com.ads.dentalapp.mapper.PatientMapper;
import com.ads.dentalapp.model.Appointment;
import com.ads.dentalapp.model.Bill;
import com.ads.dentalapp.model.Dentist;
import com.ads.dentalapp.model.Patient;
import com.ads.dentalapp.service.AppointmentService;
import com.ads.dentalapp.service.BillService;
import com.ads.dentalapp.service.DentistService;
import com.ads.dentalapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
@PreAuthorize("hasRole('PATIENT')")
public class PatientController {

    private  PatientService patientService;
    private  AppointmentService appointmentService;
    private  PatientMapper patientMapper;
    private  AppointmentMapper appointmentMapper;

    private  BillService billService;
    private  BillMapper billMapper;
    @Autowired
    private DentistService dentistService;

    @Autowired
    private DentistMapper dentistMapper;


    @GetMapping
    public String getPatientDashboard() {
        return "Welcome,  Patient!";
    }

    @Autowired
    public PatientController(
            PatientService patientService,
            AppointmentService appointmentService,
            PatientMapper patientMapper,
            AppointmentMapper appointmentMapper,
            BillService billService,
            BillMapper billMapper
    ) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.patientMapper = patientMapper;
        this.appointmentMapper = appointmentMapper;
        this.billService = billService;
        this.billMapper = billMapper;
    }

    @GetMapping("/my-appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> viewMyAppointments() {
        return ResponseEntity.ok(appointmentService.getAppointmentForLoggedPatient());
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDTO> scheduleAppointment(@RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO response = appointmentService.scheduleAppointment(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/appointments/{id}/cancel")
    public ResponseEntity<String> requestCancellation(
            @PathVariable Long id,
            @RequestBody AppointmentCancellationRequestDTO request) {
        appointmentService.requestAppointmentCancellation(id, request.reason());
        return ResponseEntity.ok("Cancellation request submitted.");
    }

    @PostMapping("/appointments/{id}/reschedule")
    public ResponseEntity<String> requestChange(
            @PathVariable Long id,
            @RequestBody AppointmentChangeRequestDTO request) {
        appointmentService.requestAppointmentChange(id, request.newDate(), request.newTime());
        return ResponseEntity.ok("Change request submitted.");
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @RequestBody PatientRequestDTO patientRequestDTO) {
//        Patient updatedPatient = patientService.updatePatient(id, patientMapper.toEntity(patientRequestDTO));
//        return ResponseEntity.ok(patientMapper.toDto(updatedPatient));
//    }

    @GetMapping("/bills")
    public ResponseEntity<List<BillResponseDTO>> getMyBills() {
        List<Bill> bills = billService.getBillsForLoggedPatient();
        return ResponseEntity.ok(billMapper.toDtoList(bills));
    }

    @GetMapping("/dentists")
    public ResponseEntity<Page<DentistResponseDTO>> getPaginatedDentists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Dentist> dentistsPage = dentistService.getPaginatedDentists(pageable);

        Page<DentistResponseDTO> dentistResponseDTOs = dentistsPage.map(dentistMapper::toDto);

        return ResponseEntity.ok(dentistResponseDTOs);
    }



}