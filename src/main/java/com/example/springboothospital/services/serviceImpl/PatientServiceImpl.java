package com.example.springboothospital.services.serviceImpl;

import com.example.springboothospital.models.Appointment;
import com.example.springboothospital.models.Hospital;
import com.example.springboothospital.models.Patient;
import com.example.springboothospital.repository.AppointmentRepo;
import com.example.springboothospital.repository.HospitalRepo;
import com.example.springboothospital.repository.PatientRepo;
import com.example.springboothospital.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final HospitalRepo hospitalRepo;
    private final AppointmentRepo appointmentRepo;

    @Override
    public List<Patient> getAllPatient(Long id) {
        return patientRepo.getAllByHospitalId(id);
    }

    @Override
    public void savePatient(Long hospitalId,Patient patient) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow();
        hospital.addPatient(patient);
        patient.setHospital(hospital);
        patientRepo.save(patient);


    }

    @Override
    public Patient finById(Long id) {
     return patientRepo.findById(id).orElseThrow();
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
            appointmentRepo.deleteById(appointments.get(i).getId());
        }
         patientRepo.deleteById(id);
    }

    @Override
    public Patient updatePatient(Long id,Patient patient) {
        Patient oldPatient = patientRepo.findById(id).orElseThrow();
        oldPatient.setFirstName(patient.getFirstName());
        oldPatient.setLastName(patient.getLastName());
        oldPatient.setGender(patient.getGender());
        oldPatient.setEmail(patient.getEmail());
        oldPatient.setPhoneNumber(patient.getPhoneNumber());
        return patientRepo.save(oldPatient);
    }
}
