package org.teachease.authservice.services;

import org.springframework.stereotype.Service;
import org.teachease.authservice.dtos.TokenResponse;
import org.teachease.authservice.enitites.Token;
import org.teachease.authservice.enitites.User;
import org.teachease.authservice.repositories.TokenRepository;
import org.teachease.authservice.repositories.UserRepository;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class TokenService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;
    public TokenService(UserRepository userRepository, TokenRepository tokenRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }
    public String generateToken(User user) {
        try{
            String token = jwtService.generateToken(user.getEmail());
            Token token1 = new Token();
            token1.setToken(token);
            token1.setUser(user);
            token1.setType("REFRESH_TOKEN");
            tokenRepository.save(token1);
            return token;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error");
        }
    }
    public TokenResponse refreshToken(String token) {
        try{
            Token tokenFromDB = tokenRepository.findByToken(token);
            if(tokenFromDB == null) {
                throw new RuntimeException("Token not found");
            }
            if(isExpired(tokenFromDB)) {
                throw new RuntimeException("Token is expired");
            }
            String newAccessToken = jwtService.generateToken(tokenFromDB.getUser().getEmail());
            return new TokenResponse(newAccessToken);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error");
        }
    }
    private boolean isExpired(Token token) {
        return token.getExpiresAt().before(new Timestamp(System.currentTimeMillis()));
    }
}
