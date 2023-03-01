package com.example.springboothospital.models;

import jakarta.persistence.*;
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
    @Column
    private String name;
    @ManyToMany(mappedBy = "departments",cascade = {REFRESH, DETACH, MERGE,PERSIST})
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
