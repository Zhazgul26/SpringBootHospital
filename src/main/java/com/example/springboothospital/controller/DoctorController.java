package com.example.springboothospital.controller;


import com.example.springboothospital.entity.Doctor;
import com.example.springboothospital.services.DepartmentService;
import com.example.springboothospital.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{id}/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    @GetMapping
    String getAllDepartments(@PathVariable("id") Long id, Model model){
        model.addAttribute("doctors",doctorService.getAll(id));
        model.addAttribute("departments",departmentService.getAll(id));
        model.addAttribute("hospitalId",id);
        return "doctor/doctors";
    }
    @GetMapping("/saveDoctor")
    String save(Model model,@PathVariable("id")Long id){
        model.addAttribute("doctor",new Doctor());
        model.addAttribute("hospitalId",id);
        return "/doctor/saveDoctor";
    }
    @PostMapping("/new")
    String create(@ModelAttribute("doctor")@Valid Doctor doctor, BindingResult bindingResult, @PathVariable("id") Long id) {
        if(bindingResult.hasErrors()){
            return "/doctor/saveDoctor";
        }
        doctorService.save(id,doctor);
        return "redirect:/{id}/doctors";
    }
    @GetMapping("/{doctorId}/departments")
    String departments(@PathVariable("id")Long id,
                       @PathVariable("doctorId")Long doctorId,
                       Model model){
        model.addAttribute("doctor", doctorService.findById(doctorId));
        model.addAttribute("departments", departmentService.getAllDepartmentByDoctorId(doctorId));
        model.addAttribute("doctors",doctorService.getAll(id));
        return "doctor/departments";
    }
    @GetMapping("/{doctorId}/assignDepartment")
    String assignToDepartment(@PathVariable("id")Long id, @PathVariable("doctorId")Long doctorId, Model model){
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("departments", departmentService.getAll(id));
        return "doctor/assignToDepartment";
    }
    @PostMapping("/{doctorId}/saveAssignDepartment")
    String saveAssignDepartment(@PathVariable("doctorId")Long doctorId, @ModelAttribute("doctor") Doctor doctor){
        doctorService.assignDoctor(doctorId,doctor);
        return "redirect:/{id}/doctors";
    }
    @GetMapping("{doctorId}/delete")
    String deleteById(@PathVariable("doctorId") Long doctorId, @PathVariable String id) {
        doctorService.delete(doctorId);
        return "redirect:/{id}/doctors";

    }
    @GetMapping("/{doctorId}/edit")
    String getUpdate(@PathVariable("doctorId") Long doctorId, Model model,@PathVariable("id") Long id) {
        model.addAttribute("doctor",doctorService.findById(doctorId));
        model.addAttribute("hospitalId",id);
        return "doctor/updateDoctor";
    }

    @PostMapping("/{doctorId}/up")
    String updateDoctor(@PathVariable("doctorId") Long doctorId, @ModelAttribute("doctor") @Valid  Doctor doctor,BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "doctor/updateDoctor";
        }
        doctorService.update(doctorId,doctor);
        return "redirect:/{id}/doctors";
    }

}