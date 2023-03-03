package com.example.springboothospital.repository;


import com.example.springboothospital.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
     @Query("select a from Appointment a where a.department.hospital.id=:id")
     List<Appointment> findAllByHospitalId(Long id);
}
