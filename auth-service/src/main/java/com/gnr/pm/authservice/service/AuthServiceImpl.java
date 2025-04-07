package com.gnr.pm.authservice.service;

import com.gnr.pm.authservice.dto.LoginRequestDTO;
import com.gnr.pm.authservice.model.User;
import com.gnr.pm.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.jar.JarException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        return userService.findByEmail(loginRequestDTO.getEmail())
                .filter(user -> passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getEmail(), user.getRole()));
    }

    @Override
    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException jwtException) {
            return false;
        }
    }

}
