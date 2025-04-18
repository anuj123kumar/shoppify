package com.anuj.shoppify.auth.controller;

import com.anuj.shoppify.auth.dto.AuthResponse;
import com.anuj.shoppify.auth.dto.UserDTO;
import com.anuj.shoppify.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> createUser(@RequestBody @Valid UserDTO userDTO) {
        authService.createUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserDTO userDTO) {
        AuthResponse response = authService.login(userDTO);
        return ResponseEntity.ok(response);
    }
}
