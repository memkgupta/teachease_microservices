package org.teachease.authservice.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.misc.MultiMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.teachease.authservice.enitites.User;
import org.teachease.authservice.repositories.UserRepository;
import org.teachease.authservice.services.CustomUserDetailsService;
import org.teachease.authservice.services.JWTService;
import org.teachease.authservice.services.TokenService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/oauth")
public class OAuthController {
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final JWTService jWTService;
    private final TokenService tokenService;
    @Value("${CLIENT_ID}")
   private String clientId;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;
    public OAuthController(CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, JWTService jWTService, TokenService tokenService) {
        this.customUserDetailsService = customUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.jWTService = jWTService;
        this.tokenService = tokenService;
    }

    @GetMapping("/google")
    public void google(@RequestParam String code, HttpServletResponse response2) {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate
        try{
            String tokenEndpoint = "https://oauth2.googleapis.com/token";

            MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);
            map.add("code", code);
            map.add("grant_type", "authorization_code");
            map.add("redirect_uri", "http://localhost:8080/api/v1/oauth/google");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(map,headers);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
            String token = tokenResponse.getBody().get("id_token").toString();
            String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + token;
            ResponseEntity<Map> response = restTemplate.getForEntity(url,Map.class);
            User user = null;
            if(response.getStatusCode()== HttpStatus.OK){
                UserDetails userDetails =null;
                String email = response.getBody().get("email").toString();;
                String firstName = response.getBody().get("first_name").toString();
                String lastName = response.getBody().get("last_name").toString();;
                try {
                    userDetails = customUserDetailsService.loadUserByEmail(email);
                }
                catch(Exception e){
                    user=new User();
                    user.setEmail(email);
                    user.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
                    user.setName(firstName + " " + lastName);
                    userRepository.save(user);
                    userDetails=customUserDetailsService.loadUserByUsername(user.getEmail());
                }



                String jwt_token = jWTService.generateToken(userDetails.getUsername());

                Cookie cookie = new Cookie("accessToken",jwt_token);
                Cookie refreshCookie = new Cookie("refreshToken",tokenService.generateToken(user));
                refreshCookie.setSecure(true);
                refreshCookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(3600);

                cookie.setSecure(true);
                response2.addCookie(cookie);
                response2.addCookie(refreshCookie);
                response2.sendRedirect("http://localhost:5172/account");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
}