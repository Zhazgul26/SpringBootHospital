package com.example.springboothospital.services.serviceImpl;

import com.example.springboothospital.models.Appointment;
import com.example.springboothospital.models.Department;
import com.example.springboothospital.models.Doctor;
import com.example.springboothospital.models.Hospital;
import com.example.springboothospital.repository.AppointmentRepo;
import com.example.springboothospital.repository.DoctorRepo;
import com.example.springboothospital.repository.HospitalRepo;
import com.example.springboothospital.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;
    private final HospitalRepo hospitalRepo;
    private final AppointmentRepo appointmentRepo;
    @Override
    public List<Doctor> getAll(Long id) {
        return doctorRepo.getAllHospitalById(id);
    }

    @Override
    public void save(Long hospitalId,Doctor doctor) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow();
        hospital.addDoctor(doctor);
        doctor.setHospital(hospital);
        doctorRepo.save(doctor);
    }


    @Override
    public Doctor findById(Long id) {
       return doctorRepo.findById(id).orElseThrow();
    }

    @Override
    public Doctor update(Long doctorId,Doctor doctor) {
        Doctor oldDoctor = findById(doctorId);
        oldDoctor.setFirsName(doctor.getFirsName());
        oldDoctor.setLastName(doctor.getLastName());
        oldDoctor.setEmail(doctor.getEmail());
        oldDoctor.setPosition(doctor.getPosition());
        return doctorRepo.save(oldDoctor);
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
            appointmentRepo.deleteById(appointments.get(i).getId());
        }
      doctorRepo.deleteById(id);
    }

    @Override
    public List<Department> getAllDepartmentDoctorById(Long doctorId) {
        return doctorRepo.getAllDepartmentDoctorById(doctorId);
    }
}
