package com.sachet.authserveramazonjava.controller;

import com.sachet.authserveramazonjava.model.User;
import com.sachet.authserveramazonjava.model.UserLogin;
import com.sachet.authserveramazonjava.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLogin login) throws Exception {
        return ResponseEntity.ok(userService.login(login));
    }

    @PutMapping("/edit")
    public ResponseEntity<User> edit(@RequestBody User user) {
        return ResponseEntity.ok(userService.editUser(user));
    }
}
