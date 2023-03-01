package com.example.springboothospital.services.serviceImpl;
import com.example.springboothospital.models.Appointment;
import com.example.springboothospital.models.Doctor;
import com.example.springboothospital.models.Hospital;
import com.example.springboothospital.models.Patient;
import com.example.springboothospital.repository.*;
import com.example.springboothospital.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final HospitalRepo hospitalRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final DepartmentRepo departmentRepo;

    @Override
    public List<Appointment> findAll(Long id) {
        return appointmentRepo.findAllByHospitalId(id);
    }

    @Override
    public void save(Long hospitalId, Long patientId, Long doctorId, Long departmentId, Appointment appointment) {
            Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow();
            hospital.addAppointment(appointment);
            Patient patient = patientRepo.findById(patientId).orElseThrow();
            appointment.setPatient(patient);
            patient.addAppointment(appointment);
            Doctor doctor = doctorRepo.findById(doctorId).orElseThrow();
            appointment.setDoctor(doctor);
            doctor.addAppointment(appointment);
            appointment.setDepartment(departmentRepo.findById(departmentId).orElseThrow());
            appointmentRepo.save(appointment);
    }

    @Override
    public Appointment getById(Long id) {
     return appointmentRepo.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(Long appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow();
        appointment.setPatient(null);
        appointment.setDepartment(null);
        appointment.setDoctor(null);
        appointmentRepo.deleteById(appointmentId);
    }

    @Override
    public Appointment update(Long hospitalId, Long patientId, Long doctorId, Long departmentId, Appointment appointment,Long appointmentId) {
        appointment.setPatient(patientRepo.findById(patientId).orElseThrow());
        appointment.setDoctor(doctorRepo.findById(doctorId).orElseThrow());
        appointment.setDepartment(departmentRepo.findById(departmentId).orElseThrow());
        Appointment oldAppointment = getById(appointmentId);
        oldAppointment.setPatient(appointment.getPatient());
        oldAppointment.setDoctor(appointment.getDoctor());
        oldAppointment.setDepartment(appointment.getDepartment());
        oldAppointment.setDate(appointment.getDate());
        return appointmentRepo.save(oldAppointment);
        }
}

