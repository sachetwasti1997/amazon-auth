package com.sachet.authserveramazonjava.controller;

import com.sachet.authserveramazonjava.model.Address;
import com.sachet.authserveramazonjava.service.AddressService;
import com.sachet.authserveramazonjava.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    private final UserService userService;
    private final AddressService addressService;

    public AddressController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<Address> addUserAddress(@RequestBody Address address, @PathVariable Integer userId) throws Exception {
        System.out.println(address);
        return ResponseEntity.ok(addressService.addAddress(address, userId));
    }

    @PutMapping("/edit")
    public ResponseEntity<Address> editUserAddress(@RequestBody Address address) throws Exception {
        return ResponseEntity.ok(addressService.editAddress(address));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Integer> deleteAddress(@PathVariable int addressId) throws Exception {
        return ResponseEntity.ok(addressService.delete(addressId));
    }
}
