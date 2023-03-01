package com.example.springboothospital.repository;
import com.example.springboothospital.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {
    @Query("select p from Patient p where p.hospital.id=:id")
    List<Patient> getAllByHospitalId(Long id);
}
