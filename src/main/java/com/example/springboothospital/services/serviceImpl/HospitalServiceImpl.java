package com.example.springboothospital.services.serviceImpl;

import com.example.springboothospital.entity.Hospital;
import com.example.springboothospital.exception.BadRequestExseption;
import com.example.springboothospital.repository.HospitalRepository;
import com.example.springboothospital.services.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;
    @Override
    public List<Hospital> getAll() {
        return hospitalRepository.findAll();
    }

    @Override
    public void save(Hospital hospital) {
            if (hospital.getName().toLowerCase().length()<1){
                for (Character i:hospital.getName().toCharArray()) {
                    if (!Character.isLetter(i)){
                       throw new BadRequestExseption("Aty jok hospital bolboit!");
                }
            }
        }else {
                try {
                    hospitalRepository.save(hospital);
            }catch (RuntimeException e){
                    System.out.println(e.getMessage());
                }
        }

    }

    @Override
    public Hospital findById(Long id) {
        return hospitalRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        hospitalRepository.deleteById(id);

    }

    @Override
    public void updateHospital(Long id, Hospital newHospital) {
        Hospital oldHospital = findById(id);
        oldHospital.setImage(newHospital.getImage());
        oldHospital.setName(newHospital.getName());
        oldHospital.setAddress(newHospital.getAddress());
        hospitalRepository.save(oldHospital);
    }
}
