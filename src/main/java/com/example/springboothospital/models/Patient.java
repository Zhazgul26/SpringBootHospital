package com.example.springboothospital.models;
import com.example.springboothospital.models.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Validated
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "patient_id_gen")
    @SequenceGenerator(name = "patient_id_gen", sequenceName = "patient_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;

    private Gender gender;
    private String email;

    @ManyToOne(cascade = {DETACH,REFRESH, MERGE,PERSIST},
            fetch = EAGER)
    private Hospital hospital;

    @OneToMany(mappedBy = "patient", cascade = ALL, fetch = EAGER)
    private List<Appointment> appointments = new ArrayList<>();
    public void addAppointment(Appointment appointment){
        if (appointments == null){
            appointments = new ArrayList<>();
        }appointments.add(appointment);
    }
}
