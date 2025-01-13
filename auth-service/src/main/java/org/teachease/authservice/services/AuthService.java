package org.teachease.authservice.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.teachease.authservice.dtos.LoginResponse;
import org.teachease.authservice.dtos.UserDTO;
import org.teachease.authservice.enitites.User;
import org.teachease.authservice.repositories.UserRepository;

@Service

public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTService jwtService;
    private final TokenService tokenService;
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JWTService jwtService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.tokenService = tokenService;
    }

    public String register(UserDTO userDTO) {

   try{
       User userAlreadyExists = userRepository.findByEmail(userDTO.email());
       if (userAlreadyExists != null) {
           throw new RuntimeException("User already exists");
       }
       User user = new User();
       user.setPassword(bCryptPasswordEncoder.encode(userDTO.password()));
       user.setEmail(userDTO.email());
       userRepository.save(user);
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       return "User registered successfully";
   }
   catch(Exception e){
e.printStackTrace();
throw new RuntimeException("Internal server error");
   }
    }
    public LoginResponse login(String email, String password) {
      try{
          User user = userRepository.findByEmail(email);
          if (user == null) {
              throw new UsernameNotFoundException("User not found");
          }
          if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
              throw new BadCredentialsException("Bad credentials");
          }
          String accessToken = jwtService.generateToken(email);
          String refreshToken = tokenService.generateToken(user);
          return new LoginResponse(accessToken, refreshToken);
      }
      catch(Exception e){
          e.printStackTrace();
          throw new RuntimeException("Internal server error");
      }
    }

    public UserDTO me(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new UserDTO(userDetails.getId(), "",userDetails.getEmail() );
    }


}