package com.ads.dentalapp.mapper;

import com.ads.dentalapp.dto.response.BillResponseDTO;
import com.ads.dentalapp.model.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BillMapper {
    BillResponseDTO toDto(Bill bill);
    List<BillResponseDTO> toDtoList(List<Bill> bills);
}
