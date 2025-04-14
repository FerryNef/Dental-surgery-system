package com.ads.dentalapp.mapper;

import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.dto.response.PatientResponseDTO;
import com.ads.dentalapp.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;


    @Mapper(componentModel = "spring")
    public interface PatientMapper {

        //@Mapping(target = "appointments", ignore = true)
        @Mapping(target = "addressId", expression = "java(patient.getAddress() != null ? patient.getAddress().getId() : null)")
        PatientResponseDTO toDto(Patient patient);

        List<PatientResponseDTO> toDtoList(List<Patient> patients);
}
