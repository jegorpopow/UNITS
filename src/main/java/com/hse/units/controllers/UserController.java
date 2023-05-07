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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

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

    @PostMapping("/registration")
    public String registration(@RequestParam() String username, @RequestParam String password, @RequestParam String email, Model model) {
        User user = new User(username, password, email);
        if (userService.createUser(user)){
            return "redirect:/login";
        }
        model.addAttribute("message", "Пользователь с таким логином уже существует");
        return "registration";
    }

    @GetMapping("/registration")
    public String registrationSubmit(Model model) {;
        return "registration";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (!isActivated) {
            model.addAttribute("message", "Пользователь активирован");
        } else {
            model.addAttribute("message", "Код активации не найден");
        }

        return "login";
    }

}
