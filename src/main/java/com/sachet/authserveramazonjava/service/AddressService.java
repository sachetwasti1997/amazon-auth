package com.sachet.authserveramazonjava.service;

import com.sachet.authserveramazonjava.model.Address;
import com.sachet.authserveramazonjava.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address editAddress(Address address) throws Exception {
        Optional<Address> savedAddress = addressRepository.findById(address.getId());
        if (savedAddress.isPresent()) {
            address.setUser(savedAddress.get().getUser());
            return addressRepository.save(address);
        }
        throw new Exception("Address Not Found");
    }

}
