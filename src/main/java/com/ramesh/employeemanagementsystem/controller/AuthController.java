package com.ramesh.employeemanagementsystem.controller;

import com.ramesh.employeemanagementsystem.model.User;
import com.ramesh.employeemanagementsystem.payload.request.AuthRequest;
import com.ramesh.employeemanagementsystem.payload.request.SignupRequest;
import com.ramesh.employeemanagementsystem.payload.response.JwtResponse;
import com.ramesh.employeemanagementsystem.payload.response.RestResponse;
import com.ramesh.employeemanagementsystem.repository.UserRepository;
import com.ramesh.employeemanagementsystem.service.JwtService;
import com.ramesh.employeemanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager,
                          UserService userService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/signup")
    public ResponseEntity<RestResponse> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userService.checkIfUsernameExist(signUpRequest.getUsername())) {
            return ResponseEntity.ok(RestResponse.success().build("Username is already taken!"));
        }

        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

        user.setRoles("ROLE_USER");
        userService.saveUser(user);

        return ResponseEntity.ok(RestResponse.success().build("User registered successfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<RestResponse> authenticateAndGetToken(@RequestBody AuthRequest request) {
        System.out.println("Inside signin api");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token;
        if (authentication.isAuthenticated()) {
            token = jwtService.generateToken(request.getUsername());
            JwtResponse response = new JwtResponse(token, request.getUsername());
            return ResponseEntity.ok(RestResponse.success().build(response));
        } else {
            return ResponseEntity.ok(RestResponse.success().build("Invalid username or password"));
        }
    }
}

