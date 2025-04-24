package com.ads.dentalapp.mapper;

import com.ads.dentalapp.dto.request.RoleRequestDTO;
import com.ads.dentalapp.dto.request.UserRequestDTO;
import com.ads.dentalapp.dto.response.UserResponseDTO;
import com.ads.dentalapp.model.Role;
import com.ads.dentalapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(source = "roleRequestDto", target = "role")
    User userRequestDtoToUser(UserRequestDTO userRequestDto);

    @Mapping(source = "role", target = "role")
    UserResponseDTO userToUserResponseDto(User user);

    @Mapping(source = "role", target = "role")
    List<UserResponseDTO> userToUserResponseDto(List<User> user);

    default Role map(RoleRequestDTO dto) {
        if (dto == null || dto.name() == null) {
            throw new IllegalArgumentException("Role name cannot be null");
        }
        return Role.valueOf(dto.name().toUpperCase());
    }
}
