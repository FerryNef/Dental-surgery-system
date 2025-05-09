package com.ads.dentalapp.repository;

import com.ads.dentalapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByOrderByCityAsc();
}
