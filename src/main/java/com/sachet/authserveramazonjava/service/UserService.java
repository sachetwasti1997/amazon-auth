package com.sachet.authserveramazonjava.service;

import com.sachet.authserveramazonjava.model.Address;
import com.sachet.authserveramazonjava.model.User;
import com.sachet.authserveramazonjava.model.UserLogin;
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

    public String login(UserLogin login) throws Exception {
        Optional<User> savedUser = userRepository.findByEmail(login.getUserEmail());
        if (savedUser.isPresent()) {
            User user = savedUser.get();
            if (!bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())){
               throw new Exception("Invalid Credentials");
            }
            return jwtService.generateToken(user);
        }
        throw new Exception("Invalid Credentials");
    }

    public User editUser(User user) {
        return userRepository.save(user);
    }

}
