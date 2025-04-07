package com.gnr.pm.authservice.service;

import com.gnr.pm.authservice.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(
            String email
    );
}
