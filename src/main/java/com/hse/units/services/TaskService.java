package com.hse.units.services;

import com.hse.units.entities.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    //private final TaskRepository taskRepository;

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
        tasks.removeIf(task -> task.getId().equals(id));

    }

    public Task getTaskById(Long id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

}
