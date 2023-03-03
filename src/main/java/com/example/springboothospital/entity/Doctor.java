package com.example.springboothospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "First Name should not be empty")
    @Size(min = 2, max = 50, message = " First should be between 2 and 50 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last Name should not be empty")
    @Size(min = 2, max = 50, message = " Last should be between 2 and 50 characters")
    private String lastName;

    @NotEmpty(message = " Position should not be empty")
    @Size(min = 2, max = 50, message = " Position should be between 2 and 50 characters")
    private String position;

    @NotEmpty(message = "Email should not be empty!")
    @Email(message = "Please provide a valid email address!")
    private String email;

    @ManyToMany(mappedBy = "doctors",cascade = {REFRESH, DETACH, MERGE,PERSIST})
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
