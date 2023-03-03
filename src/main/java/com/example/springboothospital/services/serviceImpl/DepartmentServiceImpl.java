package com.example.springboothospital.services.serviceImpl;


import com.example.springboothospital.entity.Appointment;
import com.example.springboothospital.entity.Department;
import com.example.springboothospital.entity.Hospital;
import com.example.springboothospital.repository.AppointmentRepository;
import com.example.springboothospital.repository.DepartmentRepository;
import com.example.springboothospital.repository.DoctorRepository;
import com.example.springboothospital.repository.HospitalRepository;
import com.example.springboothospital.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Department> getAll(Long id) {
        return departmentRepository.getAllByHospitalId(id);
    }

    @Override
    @Transactional
    public void save(Long id, Department department)  {
        Hospital hospital = hospitalRepository.findById(id).orElseThrow();
                hospital.addDepartment(department);
                department.setHospital(hospital);
                departmentRepository.save(department);

    }


    @Override
    public Department finById(Long id) {
       return departmentRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
      Department department = departmentRepository.findById(id).orElseThrow();
      Hospital hospital = department.getHospital();
      List<Appointment> appointments = appointmentRepository.findAllByHospitalId(hospital.getId());
      List<Appointment> appointmentList = new ArrayList<>();
      for (Appointment appointment : appointments){
          if (appointment.getDepartment().getId().equals(id)){
              appointmentList.add(appointment);
          }
      }
      appointmentList.forEach(appointment -> appointment.getDoctor().setAppointments(null));
      appointmentList.forEach(appointment -> appointment.getPatient().setAppointments(null));
      hospital.getAppointments().removeAll(appointmentList);
        for (int i = 0; i < appointmentList.size(); i++) {
            appointmentRepository.deleteById(appointmentList.get(i).getId());
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public void update(Long departmentId, Department department) {
        Department oldDepartment = finById(departmentId);
        oldDepartment.setName(department.getName());
          departmentRepository.save(oldDepartment);
    }

    @Override
    public List<Department> getAllDepartmentByDoctorId(Long id) {
        return departmentRepository.getAllDepartmentDoctorById(id);
    }

}
