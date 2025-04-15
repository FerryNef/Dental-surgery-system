package com.ads.dentalapp.mapper;

import com.ads.dentalapp.dto.response.AddressResponseDTO;
import com.ads.dentalapp.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface AddressMapper {

    @Mapping(target = "patients", source = "patients")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "zipCode", source = "zipCode")
    AddressResponseDTO toDto(Address address);

    List<AddressResponseDTO> toDtoList(List<Address> addresses);
}