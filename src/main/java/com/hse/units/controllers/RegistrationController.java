package com.hse.units.controllers;

import com.hse.units.entities.Task;
import com.hse.units.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    /*
    @Autowired
    private UserRepository userRepository;
     */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit(@RequestParam String username, @RequestParam String password, Model model) {
        System.out.println("Adfsgdhfjtdsrtgafsgdhstgragbndhrstgaefbgsafghdtrgv");
        User user = new User(username, password); //add encryption
        //userRepository.addUser(user);
        return "redirect:/login"; //authologin and move to profile/{id}
    }

}
