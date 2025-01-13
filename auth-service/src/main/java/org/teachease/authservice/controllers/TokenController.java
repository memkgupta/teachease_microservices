package org.teachease.authservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teachease.authservice.dtos.TokenResponse;
import org.teachease.authservice.services.TokenService;

@RestController
@CrossOrigin("*")
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public ResponseEntity<TokenResponse> refreshToken(final HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Missing Authorization header");
        }
        return ResponseEntity.ok(tokenService.refreshToken(token));
    }
}
