package com.sachet.authserveramazonjava.service;

import com.sachet.authserveramazonjava.model.Address;
import com.sachet.authserveramazonjava.model.User;
import com.sachet.authserveramazonjava.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
    }

    public String saveUser(User user) throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return jwtService.generateToken(userRepository.save(user));
        }
        throw new Exception("User with that email already exists, please try a different email");
    }

    public User editUser(User user) {
        return userRepository.save(user);
    }

    public Address addAddress(Address address, Integer userId) throws Exception {
        Optional<User> savedUser = userRepository.findById(userId);
        if (savedUser.isPresent()) {
            User user = savedUser.get();
            user.getAddresses().add(address);
            userRepository.save(user);
            return address;
        }
        throw new Exception("No User found!");
    }
}
