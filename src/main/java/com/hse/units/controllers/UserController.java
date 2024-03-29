package com.hse.units.controllers;

import com.hse.units.domain.Form;
import com.hse.units.domain.PercentageOfProgress;
import com.hse.units.repos.ProgressRepository;
import com.hse.units.repos.UserRepository;
import com.hse.units.domain.User;
import com.hse.units.services.FormService;
import com.hse.units.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private FormService formService;

    @Autowired
    private ProgressRepository progressRepository;

    @GetMapping("/user")
    public String user(Principal principal) {
        if (principal.getName() == null) {
            return "redirect:/login";
        }
        Long userId = userRepository.findByName(principal.getName()).get(0).getUid();
        return "redirect:/user/" + userId;
    }

    @GetMapping("/user/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findByUid(id);
        model.addAttribute("user", user);
        List<Form> forms = formService.createdForms(id);
        model.addAttribute("forms", forms);
        if (!user.getAvailableForms().equals("")) {
            List<Form> formsToSolve = Arrays.stream(user.getAvailableForms().split(" "))
                    .map(s -> formService.getFormById(Long.parseLong(s))).toList();
            model.addAttribute("formsToSolve", formsToSolve);
        }
        List<PercentageOfProgress> tags = progressRepository.findByUser(user).stream()
                .sorted((t1, t2) -> t2.getPercent().compareTo(t1.getPercent()))
                .limit(5)
                .toList();
        model.addAttribute("tags", tags);
        return "profile";
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