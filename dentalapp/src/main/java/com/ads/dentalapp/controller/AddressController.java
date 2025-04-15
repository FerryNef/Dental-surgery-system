package com.ads.dentalapp.controller;

import com.ads.dentalapp.dto.response.AddressResponseDTO;
import com.ads.dentalapp.mapper.AddressMapper;
import com.ads.dentalapp.model.Address;
import com.ads.dentalapp.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/DentalSurgery/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddressesSortedByCity();
        return ResponseEntity.ok(addressMapper.toDtoList(addresses));
    }
}
