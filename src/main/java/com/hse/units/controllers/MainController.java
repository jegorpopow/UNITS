package com.hse.units.controllers;

import com.hse.units.auth.AuthenticationController;
import com.hse.units.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.security.Security;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AuthenticationController authenticationController;
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        if (authenticationController.isAlreadyAuthorized(request)) {
            return "redirect:/user";
        }
        model.addAttribute("title", "It's a MainPage");
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("user", login);
        return "home";
    }




}