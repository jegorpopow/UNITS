package com.hse.units.controllers;

import com.hse.units.domain.Task;
import com.hse.units.services.TaskService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private void ifAuthorized(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", login);
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        ifAuthorized(model);
        model.addAttribute("tasks", taskService.getTasks());
        return "tasks";
    }

    @GetMapping("/task/{id}")
    public String taskInfo(Long id, Model model) {
        ifAuthorized(model);
        model.addAttribute("task", taskService.getTaskById(id));
        return "task";
    }

    @PostMapping("/task/add")
    public String addTask(Task task) {
        taskService.addTask(task);
        return "redirect:/";
    }

    @PostMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }

    @GetMapping("/tests")
    public String tests(Model model) {
        ifAuthorized(model);
        return "tests";
    }

}
