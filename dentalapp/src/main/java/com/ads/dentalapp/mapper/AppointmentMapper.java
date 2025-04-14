package com.ads.dentalapp.mapper;

import com.ads.dentalapp.dto.request.AppointmentRequestDTO;
import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.dto.response.PatientResponseDTO;
import com.ads.dentalapp.model.Appointment;
import com.ads.dentalapp.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    Appointment requestDtoToAppointment(AppointmentRequestDTO dto);

    @Mapping(target = "id", expression = "java(appointment.getId())")
    @Mapping(target = "dentistName", expression = "java(appointment.getDentist().getFirstName())")
    @Mapping(target = "patientName", expression = "java(appointment.getPatient().getFirstName())")
    @Mapping(target = "surgeryName", expression = "java(appointment.getSurgery().getName())")
    AppointmentResponseDTO appointmentToResponseDTO(Appointment appointment);

    List<AppointmentResponseDTO> toDtoList(List<Appointment> appointments);
    List<AppointmentResponseDTO> appointmentListToResponseDTO(List<Appointment> appointments);
}
