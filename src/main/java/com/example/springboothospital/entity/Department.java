package com.example.springboothospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
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
@Table(name = "departments")
@EqualsAndHashCode
public class Department {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "department_id_gen")
    @SequenceGenerator(name = "department_id_gen", sequenceName = "department_id_seq", allocationSize = 12)
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = " Name should be between 2 and 50 characters")
    private String name;

    @ManyToMany(cascade = {REFRESH, DETACH, MERGE,PERSIST})
    List<Doctor> doctors ;
    public void addDoctor(Doctor doctor) {
        if (doctors == null) {
            doctors = new ArrayList<>();
        } else {
            doctors.add(doctor);
        }
    }

    @ManyToOne(cascade = {REFRESH, DETACH, MERGE,PERSIST})
    private Hospital hospital;
}
