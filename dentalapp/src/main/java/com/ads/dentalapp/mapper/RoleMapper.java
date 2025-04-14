package com.ads.dentalapp.mapper;

import com.ads.dentalapp.dto.request.RoleRequestDTO;
import com.ads.dentalapp.dto.response.RoleResponseDTO;
import com.ads.dentalapp.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    Role roleRequestDtoToRole(RoleRequestDTO roleRequestDto);
    RoleResponseDTO roleToRoleResponseDto(Role role);
}
