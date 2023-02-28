package com.hse.units.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


}
