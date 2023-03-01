package com.example.springboothospital.repository;



import com.example.springboothospital.models.Department;
import com.example.springboothospital.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
    @Query("select d from Doctor  d where  d.hospital.id=:id")
    List<Doctor> getAllHospitalById(Long id);
    @Query("select d from Department  d join d.doctors doctor where doctor.id=:id")
    List<Department> getAllDepartmentDoctorById(Long Id);
}
