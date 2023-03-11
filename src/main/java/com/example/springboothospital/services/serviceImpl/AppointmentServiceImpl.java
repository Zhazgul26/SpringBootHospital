package com.example.springboothospital.services.serviceImpl;

import com.example.springboothospital.entity.Appointment;
import com.example.springboothospital.entity.Doctor;
import com.example.springboothospital.entity.Hospital;
import com.example.springboothospital.entity.Patient;
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
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<Appointment> findAll(Long id) {
        return appointmentRepository.findAllByHospitalId(id);
    }

    @Override
    public void save(Long hospitalId, Long patientId, Long doctorId, Long departmentId, Appointment appointment) {
            Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow();
            hospital.addAppointment(appointment);
            Patient patient = patientRepository.findById(patientId).orElseThrow();
            appointment.setPatient(patient);
            patient.addAppointment(appointment);
            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
            appointment.setDoctor(doctor);
            doctor.addAppointment(appointment);
            appointment.setDepartment(departmentRepository.findById(departmentId).orElseThrow());
            appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getById(Long id) {
     return appointmentRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();

        appointment.setPatient(null);
        appointment.setDepartment(null);
        appointment.setDoctor(null);
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public Appointment update(Long hospitalId, Long patientId,
                              Long doctorId, Long departmentId, Appointment appointment,Long appointmentId) {

        appointment.setPatient(patientRepository.findById(patientId).orElseThrow());
        appointment.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        appointment.setDepartment(departmentRepository.findById(departmentId).orElseThrow());

        Appointment oldAppointment = getById(appointmentId);

        oldAppointment.setPatient(appointment.getPatient());
        oldAppointment.setDoctor(appointment.getDoctor());

        oldAppointment.setDepartment(appointment.getDepartment());
        oldAppointment.setDate(appointment.getDate());

        return appointmentRepository.save(oldAppointment);
        }
}

