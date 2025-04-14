package com.ads.dentalapp.service;
import com.ads.dentalapp.dto.request.AppointmentRequestDTO;
import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.model.Appointment;
import java.util.List;

public interface AppointmentService {
    Appointment saveAppointment(Appointment appointment);
    Appointment getAppointmentById(Long id);
    List<Appointment> getAllAppointments();


    AppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO dto);
    AppointmentResponseDTO cancelAppointment(Long appointmentId);
    AppointmentResponseDTO updateAppointment(Long appointmentId, AppointmentRequestDTO dto);

    List<AppointmentResponseDTO> getAppointmentsByDentistId(Long dentistId);



}