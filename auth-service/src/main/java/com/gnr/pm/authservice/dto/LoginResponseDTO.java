package com.gnr.pm.authservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginResponseDTO {

    private final String token;
}
