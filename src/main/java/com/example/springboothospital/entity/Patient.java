package com.example.springboothospital.entity;
import com.example.springboothospital.entity.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotEmpty(message = " First Name should not be empty")
    @Size(min = 3, max = 50, message = " First Name should be between 2 and 50 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = " Last Name should not be empty")
    @Size(min = 3, max = 50, message = " Last Name should be between 2 and 50 characters")
    private String lastName;

    @Column(name = "phone_number")
    @NotEmpty(message = "Phone number should not be empty!")
    @Pattern(regexp = "\\+996\\d{9}", message = "Phone number should start with +996 and consist of 13 characters!")
    private String phoneNumber;

    @NotNull(message = "Choice gender!")
//    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotEmpty(message = "Email should not be empty!")
    @Email(message = "Please provide a valid email address!")
    @Column(name = "email", unique = true)
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
