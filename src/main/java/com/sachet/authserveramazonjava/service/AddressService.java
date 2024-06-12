package com.sachet.authserveramazonjava.service;

import com.sachet.authserveramazonjava.model.write.Address;
import com.sachet.authserveramazonjava.model.write.User;
import com.sachet.authserveramazonjava.repository.read.UserReadRepo;
import com.sachet.authserveramazonjava.repository.write.AddressRepository;
import com.sachet.authserveramazonjava.repository.write.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserReadRepo userReadRepo;

    public AddressService(AddressRepository addressRepository,
                          UserReadRepo userReadRepo) {
        this.addressRepository = addressRepository;
        this.userReadRepo = userReadRepo;
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
        Optional<User> savedUser = userReadRepo.findById(userId);
        if (savedUser.isPresent()) {
            User user = savedUser.get();
            address.setUser(user);
            return addressRepository.save(address);
        }
        throw new Exception("No User found!");
    }

    public int delete(int addressId) throws Exception {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isEmpty()) {
            throw new Exception("Cannot find the address requested");
        }
        addressRepository.deleteById(addressId);
        return addressId;
    }
}
