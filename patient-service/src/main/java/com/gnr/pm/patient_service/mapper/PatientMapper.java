package com.gnr.pm.patient_service.mapper;

import com.gnr.pm.patient_service.dto.PatientRequestDTO;
import com.gnr.pm.patient_service.dto.PatientResponseDTO;
import com.gnr.pm.patient_service.model.Patient;
import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDTO toResponseDTO(
            final Patient patient
    ) {
        return PatientResponseDTO.builder()
                .id(patient.getId().toString())
                .name(patient.getName())
                .email(patient.getEmail())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .address(patient.getAddress())
                .build();
    }

    public static Patient toPatient(
            PatientRequestDTO patientRequestDTO
    ) {
        return Patient.builder()
                .name(patientRequestDTO.getName())
                .email(patientRequestDTO.getEmail())
                .address(patientRequestDTO.getAddress())
                .dateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()))
                .registeredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()))
                .build();
    }
}
