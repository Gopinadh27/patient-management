package com.gnr.pm.patient_service.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(
            @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String message
    ) {
        super(message);
    }
}
