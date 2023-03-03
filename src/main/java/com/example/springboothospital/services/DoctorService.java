package com.example.springboothospital.services;

import com.example.springboothospital.entity.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface DoctorService {
    List<Doctor> getAll(Long id);
    void save(Long hospitalId,Doctor doctor);

    Doctor findById(Long id);
    Doctor update(Long doctorId,Doctor doctor);
    void assignDoctor(Long doctorId, Doctor doctor);
    void delete(Long id);

}
