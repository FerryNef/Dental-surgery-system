package com.ads.dentalapp.mapper;

import com.ads.dentalapp.dto.request.PatientRequestDTO;
import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.dto.response.PatientResponseDTO;
import com.ads.dentalapp.model.Address;
import com.ads.dentalapp.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


import java.util.List;


@Mapper(componentModel = "spring")
public interface PatientMapper {


    default Page<PatientResponseDTO> toDtoPage(Page<Patient> patients) {
        List<PatientResponseDTO> patientDtos = patients.map(this::toDto).getContent();
        return new PageImpl<>(patientDtos, patients.getPageable(), patients.getTotalElements());
    }

    @Mapping(target = "addressId", source = "address.id")
    PatientResponseDTO toDto(Patient patient);

    @Mapping(target = "address", source = "addressId", qualifiedByName = "mapAddressIdToAddress")
    Patient toEntity(PatientRequestDTO dto);

    List<PatientResponseDTO> toDtoList(List<Patient> patients);

    @Named("mapAddressIdToAddress")
    static Address mapAddressIdToAddress(Long addressId) {
        if (addressId == null) return null;
        Address address = new Address();
        address.setId(addressId);
        return address;
    }
}

