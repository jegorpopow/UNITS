package com.hse.units.domain;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = com.hse.units.domain.Form.TABLE_NAME)
public class Form {
    public static final String TABLE_NAME = "forms";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "form_contains",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<Task> tasks = new HashSet<>();
    String name;

    String info;

    Long creator;

    protected Form() {
    }

    public Long getId() {
        return id;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public Long getCreator() {
        return creator;
    }

    public Form(String name, String info, Long creator) {
        this.name = name;
        this.info = info;
        this.creator = creator;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addTasks(Collection<Task> tasks) {
        this.tasks.addAll(tasks);
    }
}

