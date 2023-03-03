package com.example.springboothospital.repository;



import com.example.springboothospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    @Query("select d from Doctor  d where  d.hospital.id=:id")
    List<Doctor> getAllHospitalById(Long id);

}
