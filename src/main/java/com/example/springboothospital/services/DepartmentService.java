package com.example.springboothospital.services;


import com.example.springboothospital.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAll(Long id);
    void save(Long id ,Department department) ;
    Department finById(Long id);
    void deleteById(Long id);
    void update(Long departmentId,Department department);
    List<Department> getAllDepartmentByDoctorId(Long id);
}
