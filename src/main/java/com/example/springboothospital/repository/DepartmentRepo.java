package com.example.springboothospital.repository;
;
import com.example.springboothospital.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DepartmentRepo extends JpaRepository<Department,Long> {
    @Query("select d from Department d where d.hospital.id=:id")
    List<Department> getAllByHospitalId(Long id);
}
