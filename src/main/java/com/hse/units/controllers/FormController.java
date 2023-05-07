package com.hse.units.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hse.units.domain.Form;
import com.hse.units.domain.QuickAnswer;
import com.hse.units.domain.Task;
import com.hse.units.services.FormService;
import com.hse.units.services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FormController {
    private final FormService formService;
    private final int PAGE_SIZE = 10;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    private void ifAuthorized(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", login);
    }

    @GetMapping("/forms")
    public String tasks(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        ifAuthorized(model);
        model.addAttribute("tasks", formService.getForms());
        return findPaginated(1, model);
    }

    @GetMapping("/forms/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        Page<Form> page = formService.findPaginated(pageNo, PAGE_SIZE);
        List<Form> tasks = page.getContent();
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageTotal", page.getTotalPages());
        model.addAttribute("tasks", tasks);
        return "forms";
    }

    @RequestMapping("/form/{id}")
    public String taskInfo(@PathVariable Long id, Model model) {
        ifAuthorized(model);
        Form form = formService.getFormById(id);

        model.addAttribute("form", form);

        return "form";
    }
}
