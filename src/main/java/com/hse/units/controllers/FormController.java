package com.hse.units.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hse.units.domain.*;
import com.hse.units.repos.AnswerRepository;
import com.hse.units.repos.ResponseRepository;
import com.hse.units.repos.UserRepository;
import com.hse.units.services.FormService;
import com.hse.units.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FormController {

    // TODO : auth
    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ResponseRepository responseRepository;

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
        model.addAttribute("forms", formService.getForms());
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

    /*
        @RequestMapping("/form/{id}")
        public String formInfo(@PathVariable Long id, Model model) {
            ifAuthorized(model);
            Form form = formService.getFormById(id);

            model.addAttribute("form", form);

            return "form";
        }
    */

    @RequestMapping("/form_response/{id}")
    public String responseInfo(@PathVariable Long id, Model model) {
        FormResponse response = formService.getResponse(id);
        model.addAttribute("response", response);

        Map<Task, String> answers = new HashMap<>();
        Map<Task, Boolean> correctness = new HashMap<>();

        for (Answer answer : response.getAnswers()) {
            answers.put(answer.getTask(), answer.getAnswer());
            correctness.put(answer.getTask(), answer.getResult());
        }

        model.addAttribute("answers", answers);
        model.addAttribute("correctness", correctness);

        return "response";
    }

    @RequestMapping("answer_form/{id}")
    public String formAnswer(@PathVariable Long id, HttpServletRequest request, Model model) {
        ifAuthorized(model);

        User user = null;

        if (model.containsAttribute("user")) {
            user = userRepository.findUserByName((String) model.getAttribute("user"));
        } else {
            user = userRepository.findUserByName("author");
        }

        Form form = formService.getFormById(id);
        FormResponse response = new FormResponse(user, form);

        for (Task task : form.getTasks()) {
            String rawAnswer = request.getParameter("task" + task.getId().toString());

            if (rawAnswer != null && !rawAnswer.equals("")) {
                Answer answer = new Answer(user, task, rawAnswer);
                answerRepository.save(answer);
                response.addAnswer(answer);
            }
        }

        responseRepository.save(response);

        return "redirect:/form_response/" + response.getId();
    }


    @RequestMapping("/form/{id}")
    public String taskInfo(@PathVariable Long id, Model model) {
        ifAuthorized(model);
        Form form = formService.getFormById(id);
        model.addAttribute("form", form);
        return "form";
    }

}
