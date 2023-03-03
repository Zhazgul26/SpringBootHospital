package com.example.springboothospital.controller;

import com.example.springboothospital.entity.Hospital;
import com.example.springboothospital.services.HospitalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;
    @GetMapping
    String getAll(Model model) {
        List<Hospital> hospitals = hospitalService.getAll();
        model.addAttribute("hospitals", hospitals);
        return "hospital/hospitals";
    }

    @PostMapping("/new")
    String create(@ModelAttribute("hospital")@Valid Hospital hospital, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "hospital/savePage";

        }
        hospitalService.save(hospital);
        return "redirect:/hospitals";
    }

    @GetMapping("/savePage")
    String save(Model model) {
        model.addAttribute("hospital", new Hospital());
        return "hospital/savePage";
    }

    @GetMapping("/{id}/delete")
    String deleteById(@PathVariable("id") Long id) {
        hospitalService.deleteById(id);
        return "redirect:/hospitals";
    }

    @GetMapping("/{id}/edit")
    String getUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("hospital", hospitalService.findById(id));
        return "hospital/updateHospital";
    }

    @PostMapping("/{id}/up")
    String updateHospital(@PathVariable("id") Long id, @ModelAttribute("hospital") @Valid Hospital hospital,BindingResult bindingResult ) {
        if(bindingResult.hasErrors()){
            return "hospital/updateHospital";

        }
        hospitalService.updateHospital(id, hospital);
        return "redirect:/hospitals";
    }
}
