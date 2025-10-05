package com.lgoncalves.user_service.controllers;

import com.lgoncalves.user_service.dtos.LoginDTO;
import com.lgoncalves.user_service.dtos.RegisterDTO;
import com.lgoncalves.user_service.dtos.TokenDTO;
import com.lgoncalves.user_service.services.implementations.AuthImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthImplementation authImplementation;

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody RegisterDTO request){
        TokenDTO token = authImplementation.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO request){
        TokenDTO token = authImplementation.login(request);
        return ResponseEntity.ok(token);
    }

}
