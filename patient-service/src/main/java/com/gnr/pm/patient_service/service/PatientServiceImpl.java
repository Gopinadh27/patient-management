package com.gnr.pm.patient_service.service;

import com.gnr.pm.patient_service.dto.PatientRequestDTO;
import com.gnr.pm.patient_service.dto.PatientResponseDTO;
import com.gnr.pm.patient_service.exception.EmailAlreadyExistsException;
import com.gnr.pm.patient_service.exception.PatientNotFoundException;
import com.gnr.pm.patient_service.grpc.BillingServiceGrpcClient;
import com.gnr.pm.patient_service.kafka.KafkaProducer;
import com.gnr.pm.patient_service.mapper.PatientMapper;
import com.gnr.pm.patient_service.model.Patient;
import com.gnr.pm.patient_service.repository.PatientRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final BillingServiceGrpcClient billingServiceGrpcClient;

    private final KafkaProducer kafkaProducer;

    @Override
    public List<PatientResponseDTO> getPatients() {
        return patientRepository.findAll().stream().map(PatientMapper::toResponseDTO).toList();
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already exists " + patientRequestDTO.getEmail()
            );
        }
        Patient patient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));
        billingServiceGrpcClient.createBillingAccount(patient.getId().toString()
                , patient.getName()
                , patient.getEmail());

        kafkaProducer.sendEvent(patient);
        return PatientMapper.toResponseDTO(patient);
    }

    @Override
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: "+ id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException(
                    "Email already exists " + patientRequestDTO.getEmail()
            );
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patientRepository.save(patient);
        return PatientMapper.toResponseDTO(patient);
    }

    @Override
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }


}
