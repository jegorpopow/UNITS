package com.hse.units.controllers;

import com.hse.units.domain.Task;
import com.hse.units.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/task/{id}")
    public String taskInfo(@ModelAttribute("answer") String answer, Long id, Model model) {
        ifAuthorized(model);
        Task task = null; //taskService.getTaskById(id);
        model.addAttribute("task", task);
        if (answer != null) {
            //model.addAttribute("correctness", task.checkCorrectness(answer));
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
