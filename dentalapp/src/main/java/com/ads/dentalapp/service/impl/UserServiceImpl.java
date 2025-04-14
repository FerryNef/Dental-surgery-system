package com.ads.dentalapp.service.impl;

import com.ads.dentalapp.dto.request.UserRequestDTO;
import com.ads.dentalapp.dto.response.UserResponseDTO;
import com.ads.dentalapp.mapper.UserMapper;
import com.ads.dentalapp.model.Role;
import com.ads.dentalapp.model.User;
import com.ads.dentalapp.repository.RoleRepository;
import com.ads.dentalapp.repository.UserRepository;
import com.ads.dentalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;




    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userRequestDto.username());
        if (optionalUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role role = roleRepository.findByName(userRequestDto.roleRequestDto().name())
                .orElseGet(() -> roleRepository.save(new Role(userRequestDto.roleRequestDto().name())));

        User user = userMapper.userRequestDtoToUser(userRequestDto);
        user.setRole(role);

        return userMapper.userToUserResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateUser(String username, UserRequestDTO userRequestDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userRequestDto.username());
        user.setPassword(userRequestDto.password());

        Role role = roleRepository.findByName(userRequestDto.roleRequestDto().name())
                .orElseGet(() -> roleRepository.save(new Role(userRequestDto.roleRequestDto().name())));

        user.setRole(role);

        return userMapper.userToUserResponseDto(userRepository.save(user));
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public UserResponseDTO findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::userToUserResponseDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        return userMapper.userToUserResponseDto(userRepository.findAll());
    }
}
