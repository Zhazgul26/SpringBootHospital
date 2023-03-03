package com.example.springboothospital.services;
import com.example.springboothospital.entity.Patient;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PatientService {
    List<Patient> getAllPatient(Long id);
    void savePatient(Long hospitalId,Patient patient);
    Patient finById(Long id);
    void delete(Long id);
    Patient updatePatient(Long id,Patient patient);
}
