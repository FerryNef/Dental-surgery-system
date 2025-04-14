package com.ads.dentalapp.service;
import com.ads.dentalapp.model.Address;
import java.util.List;

public interface AddressService {
    Address saveAddress(Address address);
    Address getAddressById(Long id);
    List<Address> getAllAddresses();
    void deleteAddress(Long id);
    Address updateAddress(Address address);
}
