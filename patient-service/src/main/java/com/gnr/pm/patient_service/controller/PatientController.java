package com.gnr.pm.patient_service.controller;

import com.gnr.pm.patient_service.dto.PatientRequestDTO;
import com.gnr.pm.patient_service.dto.PatientResponseDTO;
import com.gnr.pm.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "API for managing patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients(

    ) {
        return ResponseEntity.ok().body(patientService.getPatients());
    }

    @PostMapping
    @Operation(summary = "Create Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(
            @RequestBody @Valid PatientRequestDTO patientRequestDTO
    ) {
        return ResponseEntity.ok().body(patientService.createPatient(patientRequestDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable UUID id  ,
            @RequestBody @Valid PatientRequestDTO patientRequestDTO
    ) {
        return ResponseEntity.ok().body(patientService.updatePatient(id, patientRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patient")
    public ResponseEntity<Void> deletePatient(
        @PathVariable UUID id
    ) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
