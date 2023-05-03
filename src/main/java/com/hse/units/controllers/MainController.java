package com.hse.units.controllers;

import com.hse.units.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "It's a MainPage");
        return "home";
    }

    @GetMapping("/main")
    public String main(Model model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "main";
    }


}