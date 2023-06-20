package com.hse.units.domain;

import com.hse.units.repos.TaskRepository;
import com.hse.units.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FormGenerator {

    TaskRepository taskRepository;
    FormService formService;

    public FormGenerator(TaskRepository taskRepository, FormService formService) {
        this.taskRepository = taskRepository;
        this.formService = formService;
    }


    public Form generate(User user, int numberOfTask, String level, Set<TaskTag> taskTag) {
        List<Task> tasks = taskRepository.findAll().stream()
                .filter((Task t) -> !Collections.disjoint(t.getTags(), taskTag))
                .limit(numberOfTask).collect(Collectors.toList());
        Form form = new Form(tasks, user.getUid());
        formService.addForm(form);
        return form;
    }

    public Form generate(User user, int numberOfTask) {
        return null;
    }
}
