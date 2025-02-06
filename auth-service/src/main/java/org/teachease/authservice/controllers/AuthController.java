package org.teachease.authservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teachease.authservice.dtos.LoginResponse;
import org.teachease.authservice.dtos.UserDTO;
import org.teachease.authservice.services.AuthService;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO userDTO){
        System.out.println("Hello login");
        return ResponseEntity.ok(authService.login(userDTO.email(), userDTO.password()));
    }
    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDTO){
        return authService.register(userDTO);
    }
    @GetMapping("/verify")
    public ResponseEntity<?> getCurrentUser(){
        return ResponseEntity.ok(authService.me());
    }

}