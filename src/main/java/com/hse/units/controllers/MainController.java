package com.hse.units.controllers;

import com.hse.units.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.security.Security;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "It's a MainPage");
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", login);
        return "home";
    }




}