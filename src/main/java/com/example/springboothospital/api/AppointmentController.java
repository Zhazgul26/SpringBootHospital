package com.example.springboothospital.api;


import com.example.springboothospital.models.Appointment;
import com.example.springboothospital.services.AppointmentService;
import com.example.springboothospital.services.DepartmentService;
import com.example.springboothospital.services.DoctorService;
import com.example.springboothospital.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{id}/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    @GetMapping()
    String getAllAppointments(@PathVariable("id") Long id, Model model){
        model.addAttribute("appointments",appointmentService.findAll(id));
        model.addAttribute("hospitalId",id);
        return "appointment/appointments";
    }

    @GetMapping("/{doctorId}/saveAppointment")
    String save(Model model,@PathVariable("id")Long id,@PathVariable("doctorId")Long doctorId ){
        model.addAttribute("appointment",new Appointment());
        model.addAttribute("patients",patientService.getAllPatient(id));
        model.addAttribute("doctors",doctorService.getAll(id));
        model.addAttribute("departments",doctorService.getAllDepartmentDoctorById(doctorId));
        model.addAttribute("hospitalId",id);
        return "/appointment/saveAppointment";
    }
    @PostMapping("/new")
    String create(@ModelAttribute("appointment") Appointment appointment,@RequestParam("patientId")Long patientId,@RequestParam("doctorId")Long doctorId, @RequestParam("departmentId") Long departmentId,@PathVariable("id") Long hospitalId ){System.out.println("sasdd");
        appointmentService.save(hospitalId,patientId,doctorId,departmentId,appointment);
        return "redirect:/{id}/appointments";
    }
    @GetMapping("/{appointmentId}/edit")
    public String getUpdate(@PathVariable("id") Long id, @PathVariable("appointmentId") Long appointmentId, Model model){
            model.addAttribute("appointment", appointmentService.getById(appointmentId));
            model.addAttribute("doctors", doctorService.getAll(id));
            model.addAttribute("patients", patientService.getAllPatient(id));
            model.addAttribute("departments", departmentService.getAll(id));
            model.addAttribute("hospitalId",id);
        return "appointment/updateAppointment";
    }
    @PostMapping("/{appointmentId}/up")
    public String update(@ModelAttribute("appointment") Appointment appointment,@RequestParam("patientId")Long patientId,@RequestParam("doctorId")Long doctorId, @RequestParam("departmentId") Long departmentId,@PathVariable("id") Long hospitalId ,@PathVariable("appointmentId") Long appointmentId){
        appointmentService.update(hospitalId,patientId,doctorId,departmentId,appointment,appointmentId);
        return "redirect:/{id}/appointments";
    }
    @GetMapping("{appointmentId}/delete")
    String delete (@PathVariable("appointmentId") Long appointmentId) {
       appointmentService.deleteById(appointmentId);
        return "redirect:/{id}/appointments";
    }
    }
