package com.ads.dentalapp.service.impl;
import com.ads.dentalapp.dto.request.AppointmentRequestDTO;
import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.mapper.AppointmentMapper;
import com.ads.dentalapp.model.Appointment;
import com.ads.dentalapp.model.Dentist;
import com.ads.dentalapp.model.Patient;
import com.ads.dentalapp.model.Surgery;
import com.ads.dentalapp.repository.AppointmentRepository;
import com.ads.dentalapp.repository.DentistRepository;
import com.ads.dentalapp.repository.PatientRepository;
import com.ads.dentalapp.repository.SurgeryRepository;
import com.ads.dentalapp.service.AppointmentService;
import com.ads.dentalapp.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public AppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (billService.hasUnpaidBills(dto.patientId())) {
            throw new IllegalStateException("Cannot schedule appointment. Unpaid bills found.");
        }

        Dentist dentist = dentistRepository.findById(dto.dentistId())
                .orElseThrow(() -> new RuntimeException("Dentist not found"));

        Surgery surgery = surgeryRepository.findById(dto.surgeryId())
                .orElseThrow(() -> new RuntimeException("Surgery not found"));

        Appointment appointment = appointmentMapper.requestDtoToAppointment(dto);
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setSurgery(surgery);
        appointment.setStatus("Scheduled");
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

        appt.setStatus("Cancelled");
        return appointmentMapper.appointmentToResponseDTO(appointmentRepository.save(appt));
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appt.getDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Cannot update past appointment");
        }

        appt.setDate(dto.date());
        appt.setTime(dto.time());

        return appointmentMapper.appointmentToResponseDTO(appointmentRepository.save(appt));
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDentistId(Long dentistId) {
        return appointmentMapper.appointmentListToResponseDTO(
                appointmentRepository.findByDentistId(dentistId)
        );
    }
}

