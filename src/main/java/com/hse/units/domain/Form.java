package com.hse.units.domain;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = com.hse.units.domain.Form.TABLE_NAME)
public class Form {
    public static final String TABLE_NAME = "forms";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinTable(
            name = "form_contains",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> tasks = new ArrayList<>();
    String name;

    String info;

    Long creator;

    boolean shuffled;

    protected Form() {
    }

    public Long getId() {
        return id;
    }

    public List<Task> getTasks() {
        List<Task> result = new ArrayList<>(tasks);
        if (shuffled) {
            Collections.shuffle(result);
        }
        return result;
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

    public Form(String name, String info, Long creator, boolean shuffled) {
        this.name = name;
        this.info = info;
        this.creator = creator;
        this.shuffled = shuffled;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addTasks(Collection<Task> tasks) {
        this.tasks.addAll(tasks);
    }
}

