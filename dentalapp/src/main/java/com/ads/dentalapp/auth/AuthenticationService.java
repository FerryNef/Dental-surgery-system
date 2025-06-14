package com.ads.dentalapp.auth;

import com.ads.dentalapp.config.JwtService;
import com.ads.dentalapp.model.*;
import com.ads.dentalapp.repository.DentistRepository;
import com.ads.dentalapp.repository.PatientRepository;
import com.ads.dentalapp.repository.UserRepository;
import com.ads.dentalapp.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;
    private final AddressService addressService;




    public AuthenticationResponse register(RegisterRequest request) {
        Role role = request.role();

        User user = new User(
                request.email(),
                request.firstName(),
                request.lastName(),
                passwordEncoder.encode(request.password()),
                role
        );
        if (role == Role.PATIENT) {
            Address addr = new Address();
            addr.setStreet(request.street());
            addr.setCity(request.city());
            addr.setState(request.state());
            addr.setZipCode(request.zipCode());
            addr.setCountry(request.country());
            Address savedAddr = addressService.saveAddress(addr);

            Patient patient = new Patient();
            patient.setFirstName(request.firstName());
            patient.setLastName(request.lastName());
            patient.setEmail(request.email());
            patient.setPhone(request.phone());
            patient.setDateOfBirth(request.dateOfBirth());
            patient.setAddress(savedAddr);

            Patient savedPatient = patientRepository.save(patient);

            user.setPatient(savedPatient);
            savedPatient.setUser(user);

        } else if (role == Role.DENTIST) {
            Dentist dentist = dentistRepository.findByEmail(request.email())
                    .orElseThrow(() -> new RuntimeException("Dentist not found"));
            user.setDentist(dentist);
            dentist.setUser(user);
        }

        User registerUser = userRepository.save(user);
        String token = jwtService.generateToken(registerUser);
        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        User user = userRepository.findByUsername(request.username()).orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }


}
