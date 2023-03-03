package com.example.springboothospital.controller;
import com.example.springboothospital.entity.Department;
import com.example.springboothospital.services.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/{id}/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    String getAllDepartments(@PathVariable("id") Long id, Model model) {
        List<Department> departments = departmentService.getAll(id);
        model.addAttribute("departments", departments);
        model.addAttribute("hospitalId", id);
        return "department/departments";
    }

    @GetMapping("/saveDepartment")
    String save(Model model, @PathVariable("id") Long id) {
        model.addAttribute("department", new Department());
        model.addAttribute("hospitalId", id);
        return "/department/saveDepartment";
    }

    @PostMapping("/new")
    String create(@ModelAttribute("department") @Valid Department department, BindingResult bindingResult,
                  @PathVariable("id") Long id) throws Exception {
        if(bindingResult.hasErrors()) {
            return "department/saveDepartment";
        }
        departmentService.save(id, department);
        return "redirect:/{id}/departments";
    }

    @GetMapping("{departmentId}/delete")
    String delete (@PathVariable("departmentId") Long departmentId) {
        departmentService.deleteById(departmentId);
        return "redirect:/{id}/departments";
    }
    @GetMapping("/{departmentId}/edit")
    String getUpdate(@PathVariable("departmentId") Long departmentId, Model model,@PathVariable("id") Long id) {
        model.addAttribute("department",departmentService.finById(departmentId));
        model.addAttribute("hospitalId",id);
        return "department/updateDepartment";
    }

    @PostMapping("/{departmentId}/up")
    String updateDepartment(@PathVariable("departmentId") Long departmentId, @ModelAttribute("department")
    @Valid Department department, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "department/updateDepartment";
        }
        departmentService.update(departmentId,department);
        return "redirect:/{id}/departments";
    }
}