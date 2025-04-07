package com.gnr.pm.patient_service.service;

import com.gnr.pm.patient_service.dto.PatientRequestDTO;
import com.gnr.pm.patient_service.dto.PatientResponseDTO;
import java.util.List;
import java.util.UUID;

public interface PatientService {

    List<PatientResponseDTO> getPatients();

    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);

    PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO);

    void deletePatient(UUID id);
}
