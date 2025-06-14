package com.ads.dentalapp.service.impl;
import com.ads.dentalapp.dto.request.AppointmentRequestDTO;
import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.dto.response.PatientResponseDTO;
import com.ads.dentalapp.exception.AppointmentConflictException;
import com.ads.dentalapp.exception.UnpaidBillException;
import com.ads.dentalapp.mapper.AppointmentMapper;
import com.ads.dentalapp.mapper.PatientMapper;
import com.ads.dentalapp.model.*;
import com.ads.dentalapp.repository.*;
import com.ads.dentalapp.service.AppointmentService;
import com.ads.dentalapp.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final BillService billService;
    private final DentistRepository dentistRepository;
    private final SurgeryRepository surgeryRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;
    private final PatientMapper patientMapper;

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public PatientResponseDTO getPatientForAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new RuntimeException("Appointment not found"));

        Patient patient = appointment.getPatient();
        return patientMapper.toDto(patient);
    }
    @Override
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return appointmentRepository.findByPatient(patient);
    }



    @Override
    public AppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (billService.hasUnpaidBills(dto.patientId())) {
            throw new UnpaidBillException("Cannot schedule appointment. Unpaid bills found.");
        }

        Dentist dentist = dentistRepository.findById(dto.dentistId())
                .orElseThrow(() -> new RuntimeException("Dentist not found"));

        if (appointmentRepository.existsByDentistIdAndDateAndTime(dto.dentistId(), dto.date(), dto.time())) {
            throw new AppointmentConflictException("Appointment conflict: Dr." +dentist.getFirstName() +
                            "is already booked on" + dto.date() +" at " + dto.time());
        }

        LocalDate startDate = dto.date().with(DayOfWeek.MONDAY);
        LocalDate endDate = startDate.plusDays(6);

        long appointmentCount = appointmentRepository.countAppointmentsByDentistAndWeek(dentist.getId(), startDate, endDate);
        if (appointmentCount >= 5) {
            throw new AppointmentConflictException("Dentist already has 5 appointments scheduled for the week of " + dto.date());
        }



        Surgery surgery = surgeryRepository.findById(dto.surgeryId())
                .orElseThrow(() -> new RuntimeException("Surgery not found"));

        Appointment appointment = appointmentMapper.requestDtoToAppointment(dto);
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setSurgery(surgery);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setApproved(true);

        Appointment saved = appointmentRepository.save(appointment);
        return appointmentMapper.appointmentToResponseDTO(saved);
    }

    @Override
    public AppointmentResponseDTO cancelAppointment(Long appointmentId) {
        Appointment appt = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appt.getDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Cannot cancel past appointment");
        }
        if (appt.getStatus() != AppointmentStatus.REQUESTED_CANCEL) {
            throw new RuntimeException("Appointment must be requested for cancellation by the patient.");
        }

        appt.setStatus(AppointmentStatus.CANCELLED);
        appt.setApproved(true);
        return appointmentMapper.appointmentToResponseDTO(appointmentRepository.save(appt));
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appt.getStatus() != AppointmentStatus.REQUESTED_CHANGE) {
            throw new RuntimeException("Appointment must be requested for cancellation by the patient.");
        }

        appt.setDate(dto.date());
        appt.setTime(dto.time());
        appt.setStatus(AppointmentStatus.SCHEDULED);
        appt.setApproved(true);

        return appointmentMapper.appointmentToResponseDTO(appointmentRepository.save(appt));
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentForLoggedDentist() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Dentist dentist = dentistRepository.findByUser(user)
                .orElseThrow(()-> new RuntimeException("Dentist not found"));
        List<Appointment> appointments = appointmentRepository.findByDentist(dentist);
        return appointmentMapper.toDtoList(appointments);
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentForLoggedPatient() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("user not found"));

        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(()-> new RuntimeException("Patient not found"));

        List<Appointment> appointments = appointmentRepository.findByPatient(patient);
        return appointmentMapper.toDtoList(appointments);
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDentistId(Long dentistId) {
        return appointmentMapper.appointmentListToResponseDTO(
                appointmentRepository.findByDentistId(dentistId)
        );
    }

    @Override
    public void requestAppointmentCancellation(Long appointmentId, String reason) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.REQUESTED_CANCEL);
        appointment.setApproved(false);
        appointment.setNote(reason);

        appointmentRepository.save(appointment);
    }

    @Override
    public void requestAppointmentChange(Long appointmentId, String newDate, String newTime) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.REQUESTED_CHANGE);
        appointment.setApproved(false);
        appointment.setNote("Patient requested change to: " + newDate + " at " + newTime);

        appointmentRepository.save(appointment);
    }
    public void acceptAppointmentChange(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.REQUESTED_CHANGE) {
            throw new RuntimeException("No change request to accept.");
        }

        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointmentRepository.save(appointment);
    }

    public void acceptAppointmentCancellation(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.REQUESTED_CANCEL) {
            throw new RuntimeException("No cancellation request to accept.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setApproved(true);  // Mark the cancellation as approved by the office manager

        appointmentRepository.save(appointment);  // Save the updated appointment
    }


}

