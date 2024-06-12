package com.sachet.authserveramazonjava.controller;

import com.sachet.authserveramazonjava.model.write.User;
import com.sachet.authserveramazonjava.model.UserLogin;
import com.sachet.authserveramazonjava.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class AuthController {

    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization")String token)
    throws Exception{
        String bearer = "bearer ";
        token = token.substring(bearer.length()-1);
        User user = userService.getUser(token);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLogin login) throws Exception {
        LOGGER.info("/login, received a login request "+login);
        return ResponseEntity.ok(userService.login(login));
    }

    @PutMapping("/edit/{password}")
    public ResponseEntity<User> edit(@RequestBody User user, @PathVariable String password) {
        return ResponseEntity.ok(userService.editUser(user, password));
    }
}
