package com.example.springboothospital.services;


import com.example.springboothospital.models.Department;
import com.example.springboothospital.models.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface DoctorService {
    List<Doctor> getAll(Long id);
    void save(Long hospitalId,Doctor doctor);

    Doctor findById(Long id);
    Doctor update(Long doctorId,Doctor doctor);
    void delete(Long id);
    List<Department> getAllDepartmentDoctorById(Long doctorId);

}
