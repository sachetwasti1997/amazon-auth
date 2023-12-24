package com.sachet.authserveramazonjava.service;

import com.sachet.authserveramazonjava.model.Address;
import com.sachet.authserveramazonjava.model.User;
import com.sachet.authserveramazonjava.repository.AddressRepository;
import com.sachet.authserveramazonjava.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository,
                          UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Address editAddress(Address address) throws Exception {
        Optional<Address> savedAddress = addressRepository.findById(address.getId());
        if (savedAddress.isPresent()) {
            address.setUser(savedAddress.get().getUser());
            return addressRepository.save(address);
        }
        throw new Exception("Address Not Found");
    }

    public Address addAddress(Address address, Integer userId) throws Exception {
        Optional<User> savedUser = userRepository.findById(userId);
        if (savedUser.isPresent()) {
            User user = savedUser.get();
            address.setUser(user);
            return addressRepository.save(address);
        }
        throw new Exception("No User found!");
    }
}
