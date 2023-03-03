package com.example.springboothospital.services.serviceImpl;

import com.example.springboothospital.entity.Appointment;
import com.example.springboothospital.entity.Hospital;
import com.example.springboothospital.entity.Patient;
import com.example.springboothospital.repository.AppointmentRepository;
import com.example.springboothospital.repository.HospitalRepository;
import com.example.springboothospital.repository.PatientRepository;
import com.example.springboothospital.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Patient> getAllPatient(Long id) {
        return patientRepository.getAllByHospitalId(id);
    }

    @Override
    public void savePatient(Long hospitalId,Patient patient) {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow();
        hospital.addPatient(patient);
        patient.setHospital(hospital);
        patientRepository.save(patient);


    }

    @Override
    public Patient finById(Long id) {
     return patientRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(Long id) {
        Patient patient = finById(id);
        Hospital hospital = patient.getHospital();
        List<Appointment> appointments = patient.getAppointments();
        appointments.forEach(appointment -> appointment.getPatient().setAppointments(null));
        appointments.forEach(appointment -> appointment.getDoctor().setAppointments(null));
        hospital.getAppointments().removeAll(appointments);
        for (int i = 0; i < appointments.size(); i++) {
            appointmentRepository.deleteById(appointments.get(i).getId());
        }
         patientRepository.deleteById(id);
    }

    @Override
    public Patient updatePatient(Long id,Patient patient) {
        Patient oldPatient = patientRepository.findById(id).orElseThrow();
        oldPatient.setFirstName(patient.getFirstName());
        oldPatient.setLastName(patient.getLastName());
        oldPatient.setGender(patient.getGender());
        oldPatient.setEmail(patient.getEmail());
        oldPatient.setPhoneNumber(patient.getPhoneNumber());
        return patientRepository.save(oldPatient);
    }
}
