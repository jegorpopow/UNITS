package com.hse.units.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hse.units.domain.QuickAnswer;
import com.hse.units.domain.Task;
import com.hse.units.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;

@Controller
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        model.addAttribute("tasks", taskService.getTasks());
        return "tasks";
    }

    @PostMapping("/quickcheck")
    public @ResponseBody String quickcheck(@RequestBody String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            QuickAnswer answer = objectMapper.readValue(body, QuickAnswer.class);
            Task task = taskService.getTaskById(answer.getTaskId());
            if (task == null) {
                return "{\"status\": \"error\", \"reason\": \"no such task\"}";
            } else if (task.checkCorrectness(answer.getAnswer())) {
                return "{\"status\": \"ok\", \"reason\": \"correct\"}";
            } else {
                return "{\"status\": \"ok\", \"reason\": \"wrong\"}";
            }
        } catch (JsonProcessingException e) {
            return "{\"status\": \"error\", \"reason\": \"bad request body\"}";
        }
    }

    @GetMapping("/task/{id}")
    public String taskInfo(Long id, Model model) {
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
    public String tests() {
        return "tests";
    }

}
