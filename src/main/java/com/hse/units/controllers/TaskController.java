package com.hse.units.controllers;

import com.hse.units.domain.Task;
import com.hse.units.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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

    @GetMapping("/quickcheck")
    public @ResponseBody String quickcheck(@RequestParam long taskId, @RequestParam String answer) {
        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            return "{\"status\": \"error\"}";
        } else if (task.checkCorrectness(answer)) {
            return "{\"status\": \"success\", \"result\": \"correct\"}";
        } else {
            return "{\"status\": \"success\", \"result\": \"wrong\"}";
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
