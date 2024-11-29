package com.spring3.oauth.jwt.controllers;


import com.spring3.oauth.jwt.dtos.*;
import com.spring3.oauth.jwt.models.RefreshToken;
import com.spring3.oauth.jwt.services.JwtAuthenticationService;
import com.spring3.oauth.jwt.services.RefreshTokenService;
import com.spring3.oauth.jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    private RefreshTokenService refreshTokenService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/save")
    public ResponseEntity saveUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        try {
            List<UserResponse> userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        try {
            UserResponse userResponse = userService.getUser();
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public JwtTokenResponse AuthenticateAndGetToken(@RequestBody AuthTokenResponse authTokenResponse){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authTokenResponse.getUsername(), authTokenResponse.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authTokenResponse.getUsername());
            return JwtTokenResponse.builder()
                    .accessToken(jwtAuthenticationService.GenerateToken(authTokenResponse.getUsername()))
                    .token(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }


    @PostMapping("/refreshToken")
    public JwtTokenResponse refreshToken(@RequestBody TokenRenewalRequestDTO tokenRenewalRequestDTO){
        return refreshTokenService.findByToken(tokenRenewalRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtAuthenticationService.GenerateToken(userInfo.getUsername());
                    return JwtTokenResponse.builder()
                            .accessToken(accessToken)
                            .token(tokenRenewalRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

}
