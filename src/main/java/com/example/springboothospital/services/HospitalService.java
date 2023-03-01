package com.example.springboothospital.services;


import com.example.springboothospital.models.Hospital;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface HospitalService {
    List<Hospital> getAll();
    void save(Hospital hospital);
    Hospital findById(Long id);
    void deleteById(Long id);
    void updateHospital(Long id,Hospital newHospital);
}
