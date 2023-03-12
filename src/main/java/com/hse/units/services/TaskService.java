package com.hse.units.services;

import com.hse.units.entities.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private List<Task> tasks = new ArrayList<>();

    Long taskId = 0L;

    {
        tasks.add(new Task(taskId++, "Task0", "this is task 0", "0", 0L));
        tasks.add(new Task(taskId++,"Task1", "this is task 1", "0", 0L));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(Long id) {
        tasks.removeIf(task -> task.getId() == id);
    }

}
