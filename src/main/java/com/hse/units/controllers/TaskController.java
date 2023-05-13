package com.hse.units.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hse.units.domain.QuickAnswer;
import com.hse.units.domain.Task;
import com.hse.units.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final int PAGE_SIZE = 1;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private void ifAuthorized(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", login);
    }

    @GetMapping("/tasks")
    public String tasks(Model model, @PageableDefault(sort = { "id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        ifAuthorized(model);
        model.addAttribute("tasks", taskService.getTasks());
        return findPaginated(1, model);
    }

    @GetMapping("/tasks/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo, Model model) {
        Page<Task> page = taskService.findPaginated(pageNo, PAGE_SIZE);
        List<Task> tasks = page.getContent();
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageTotal", page.getTotalPages());
        model.addAttribute("tasks", tasks);
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
                return "{\"status\": \"ok\", \"result\": \"correct\"}";
            } else {
                return "{\"status\": \"ok\", \"result\": \"wrong\"}";
            }
        } catch (JsonProcessingException e) {
            return "{\"status\": \"error\", \"reason\": \"bad request body\"}";
        }
    }


    @RequestMapping("/task/{id}")
    public String taskInfo(@ModelAttribute("answer") String answer, @PathVariable Long id, Model model) {
        ifAuthorized(model);
        Task task = taskService.getTaskById(id);

        model.addAttribute("task", task);

        if (answer != null && !answer.equals("")) {
            model.addAttribute("correctness", task.checkCorrectness(answer));
        }
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
