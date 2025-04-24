package com.ads.dentalapp.mapper;

import com.ads.dentalapp.dto.request.DentistRequestDTO;
import com.ads.dentalapp.dto.response.DentistResponseDTO;
import com.ads.dentalapp.model.Dentist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DentistMapper {
    Dentist toEntity(DentistRequestDTO dto);
    DentistResponseDTO toDto(Dentist dentist);
}