package com.example.springboothospital.services.serviceImpl;

import com.example.springboothospital.entity.Appointment;
import com.example.springboothospital.entity.Department;
import com.example.springboothospital.entity.Doctor;
import com.example.springboothospital.entity.Hospital;
import com.example.springboothospital.repository.AppointmentRepository;
import com.example.springboothospital.repository.DepartmentRepository;
import com.example.springboothospital.repository.DoctorRepository;
import com.example.springboothospital.repository.HospitalRepository;
import com.example.springboothospital.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;
    private final DepartmentRepository departmentRepository;
    @Override
    public List<Doctor> getAll(Long id) {
        return doctorRepository.getAllHospitalById(id);
    }

    @Override
    public void save(Long hospitalId,Doctor doctor) {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow();
        hospital.addDoctor(doctor);
        doctor.setHospital(hospital);
        doctorRepository.save(doctor);
    }


    @Override
    public Doctor findById(Long id) {
       return doctorRepository.findById(id).orElseThrow();
    }

    @Override
    public Doctor update(Long doctorId,Doctor doctor) {
        Doctor oldDoctor = findById(doctorId);
        oldDoctor.setFirstName(doctor.getFirstName());
        oldDoctor.setLastName(doctor.getLastName());
        oldDoctor.setEmail(doctor.getEmail());
        oldDoctor.setPosition(doctor.getPosition());
        return doctorRepository.save(oldDoctor);
    }

    @Override
    public void assignDoctor(Long doctorId, Doctor doctor) {
        Department department = departmentRepository.findById(doctor.getDepartmentId()).orElseThrow();
        Doctor oldDoctor = doctorRepository.findById(doctorId).orElseThrow();
        oldDoctor.addDepartment(department);
        department.addDoctor(oldDoctor);
        doctorRepository.save(oldDoctor);
    }

    @Override
    public void delete(Long id) {
        Doctor doctor = findById(id);
        Hospital hospital = doctor.getHospital();
        List<Appointment> appointments = doctor.getAppointments();
        doctor.setDepartments(null);
       appointments.forEach(appointment -> appointment.getDoctor().setAppointments(null));
       appointments.forEach(appointment -> appointment.getPatient().setAppointments(null));
       appointments.forEach(appointment -> appointment.getDepartment().setDoctors(null));
       appointments.forEach(appointment -> appointment.getDoctor().setDepartments(null));
        hospital.getAppointments().removeAll(appointments);
        for (int i = 0; i < appointments.size(); i++) {
            appointmentRepository.deleteById(appointments.get(i).getId());
        }
      doctorRepository.deleteById(id);
    }

}
