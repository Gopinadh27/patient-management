package com.gnr.pm.patient_service.repository;

import com.gnr.pm.patient_service.model.Patient;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(final String email);

    boolean existsByEmailAndIdNot(String email, UUID id);
}
