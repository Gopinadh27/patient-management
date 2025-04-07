package com.gnr.pm.authservice.service;

import com.gnr.pm.authservice.dto.LoginRequestDTO;

import java.util.Optional;

public interface AuthService {
    Optional<String> authenticate(LoginRequestDTO loginRequestDTO);

    boolean validateToken(String substring);
}
