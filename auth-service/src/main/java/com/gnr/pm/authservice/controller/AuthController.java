package com.gnr.pm.authservice.controller;

import com.gnr.pm.authservice.dto.LoginRequestDTO;
import com.gnr.pm.authservice.dto.LoginResponseDTO;
import com.gnr.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        return tokenOptional.map(s -> ResponseEntity.ok(new LoginResponseDTO(s)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

    }

    @Operation(summary = "Validate token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        if(authorizationHeader==null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(authorizationHeader.substring(7)) ?
            ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
