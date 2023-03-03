package com.example.springboothospital.config;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager configure() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();

        UserDetails admin = userBuilder
                .username("Zhazgul")
                .password("2604")
                .roles("ADMIN")
                .build();

        UserDetails doctors = userBuilder
                .username("Aruuke")
                .password("2812")
                .roles("DOCTOR")
                .build();

        UserDetails patients = userBuilder
                .username("Aidanek")
                .password("1015")
                .roles("PATIENTS")
                .build();

        return new InMemoryUserDetailsManager(admin, doctors, patients);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(aut -> aut
                        .requestMatchers("/hospitals").permitAll().
                        requestMatchers("/hospitals/savePage").hasAnyRole("ADMIN").
                        requestMatchers("/hospitals/new").hasAnyRole("ADMIN").
                        requestMatchers("/hospitals/{id}/delete").hasAnyRole("ADMIN").
                        requestMatchers("/hospitals/{id}/edit").hasAnyRole("ADMIN").
                        requestMatchers("/hospitals/{id}/up").hasAnyRole("ADMIN").

                        requestMatchers("/{id}/appointments").permitAll()
                        .requestMatchers("/{id}/appointments/saveAppointment").permitAll()
                        .requestMatchers("/{id}/appointments/new").permitAll()
                        .requestMatchers("/{id}/appointments/{appointmentId}/edit").permitAll()
                        .requestMatchers("/{id}/appointments/{appointmentId}/up").permitAll()
                        .requestMatchers("/{id}/appointments/{appointmentId}/delete").permitAll()

                        .requestMatchers("/{id}/departments").permitAll()
                        .requestMatchers("/{id}/departments/saveDepartment").hasAnyRole("ADMIN")
                        .requestMatchers("/{id}/departments/new").hasAnyRole("ADMIN")
                        .requestMatchers("/{id}/departments/{departmentId}/edit").hasAnyRole("ADMIN")
                        .requestMatchers("/{id}/departments/{departmentId}/up").hasAnyRole("ADMIN")
                        .requestMatchers("/{id}/departments/{departmentId}/delete").hasAnyRole("ADMIN")

                        .requestMatchers("/{id}/doctors").permitAll()
                        .requestMatchers("/{id}/doctors/saveDoctor").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/doctors/new").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/doctors/{doctorId}/departments").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/doctors/{doctorId}/assignDepartment").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/doctors/{doctorId}/saveAssignDepartment").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/doctors/{doctorId}/delete").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/doctors/{doctorId}/edit").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/doctors/{doctorId}/up").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/doctors/{doctorId}/{departmentId}/delete").hasAnyRole("ADMIN", "DOCTOR")

                        .requestMatchers("/{id}/patients").permitAll()
                        .requestMatchers("/{id}/patients/savePatient").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/patients/new").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/patients/{patientId}/edit").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/patients/{patientId}/up").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/{id}/patients/{patientId}/delete").hasAnyRole("ADMIN", "DOCTOR").anyRequest().authenticated()).
                formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults()).build();

        return http.build();

    }

}


