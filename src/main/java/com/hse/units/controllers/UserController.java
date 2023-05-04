package com.hse.units.controllers;

import com.hse.units.repos.UserRepository;
import com.hse.units.domain.User;
import com.hse.units.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UserController {

    private UserRepository userRepository;
    private UserService userService = new UserService();

    @GetMapping("/user")
    public String user(Principal principal) {
        if (principal.getName() == null) {
            return "redirect:/login";
        }
        Long userId = userService.findUserByUsername(principal.getName());
        return "redirect:/user/" + userId;
    }

    @GetMapping("/user/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit(@RequestParam String username, @RequestParam String password, Model model) {
        User user = new User(username, password, null); //add encryption
        //userRepository.addUser(user);
        return "redirect:/login"; //authologin and move to profile/{id}
    }

}
