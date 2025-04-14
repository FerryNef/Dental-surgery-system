package com.ads.dentalapp.service;

import com.ads.dentalapp.dto.request.UserRequestDTO;
import com.ads.dentalapp.dto.response.UserResponseDTO;
import com.ads.dentalapp.model.User;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDto);
    UserResponseDTO updateUser(String username, UserRequestDTO userRequestDto);
    void deleteUserByUsername(String username);
    UserResponseDTO findUserByUsername(String username);
    List<UserResponseDTO> findAllUsers();

}
