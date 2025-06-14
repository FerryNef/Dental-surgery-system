package com.ads.dentalapp.service;
import com.ads.dentalapp.dto.request.AppointmentRequestDTO;
import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.dto.response.PatientResponseDTO;
import com.ads.dentalapp.model.Appointment;
import java.util.List;

public interface AppointmentService {
    Appointment saveAppointment(Appointment appointment);
    Appointment getAppointmentById(Long id);
    List<Appointment> getAllAppointments();

    PatientResponseDTO getPatientForAppointment(Long appointmentId);

    List<Appointment> getAppointmentsByPatientId(Long patientId);

    AppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO dto);
    AppointmentResponseDTO cancelAppointment(Long appointmentId);
    AppointmentResponseDTO updateAppointment(Long appointmentId, AppointmentRequestDTO dto);

    List<AppointmentResponseDTO> getAppointmentForLoggedDentist();

    List<AppointmentResponseDTO> getAppointmentForLoggedPatient();

    void requestAppointmentCancellation(Long appointmentId, String reason);
    void requestAppointmentChange(Long appointmentId,String newDate, String newTime);

     void acceptAppointmentChange(Long appointmentId);

     void acceptAppointmentCancellation(Long appointmentId);


    List<AppointmentResponseDTO> getAppointmentsByDentistId(Long dentistId);



}