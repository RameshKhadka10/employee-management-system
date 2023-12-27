package com.ramesh.employeemanagementsystem.controller;

import com.ramesh.employeemanagementsystem.dto.AuthRequest;
import com.ramesh.employeemanagementsystem.dto.SignupRequest;
import com.ramesh.employeemanagementsystem.exception.NotFoundException;
import com.ramesh.employeemanagementsystem.model.User;
import com.ramesh.employeemanagementsystem.payload.response.RestResponse;
import com.ramesh.employeemanagementsystem.repository.UserRepository;
import com.ramesh.employeemanagementsystem.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    private final UserRepository userRepository;

    @Autowired
    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<RestResponse> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.ok(RestResponse.success().build("Username is already taken!"));
        }

        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

        user.setRoles("ROLE_USER");
        userRepository.save(user);

        return ResponseEntity.ok(RestResponse.success().build("User registered successfully!"));

    }

    @PostMapping("/signin")
    public String authenticateAndGetToken(@RequestBody AuthRequest request) {
        System.out.println("Inside signin api");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken(request.getUsername());
        else
            throw new NotFoundException("User not found");
    }
}
