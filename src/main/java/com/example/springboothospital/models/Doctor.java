package com.example.springboothospital.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "doctor_id_gen")
    @SequenceGenerator(name = "doctor_id_gen",sequenceName = "doctor_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firsName;
    @Column(name = "last_name")
    private String lastName;

    private String position;

    private String email;

    @ManyToMany(cascade = {REFRESH, DETACH, MERGE,PERSIST})
    private List<Department> departments ;
    public  void addDepartment(Department department){
        if (departments == null){
            departments = new ArrayList<>();
        }else {
            departments.add(department);
        }
    }

    @ManyToOne(cascade = {REFRESH, DETACH, MERGE,PERSIST})
    private Hospital hospital;
    @OneToMany(mappedBy = "doctor",cascade = ALL)
    private List<Appointment> appointments;
    public void addAppointment(Appointment appointment){
        if (appointments == null){
            appointments = new ArrayList<>();
        }appointments.add(appointment);
    }
    @Transient
    private Long departmentId;
}
