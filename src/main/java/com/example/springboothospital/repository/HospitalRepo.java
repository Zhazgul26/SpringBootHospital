package com.example.springboothospital.repository;

import com.example.springboothospital.models.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepo extends JpaRepository<Hospital,Long> {

}
