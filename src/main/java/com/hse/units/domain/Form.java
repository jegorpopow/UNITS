package com.hse.units.domain;

import com.hse.units.repos.UserRepository;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = com.hse.units.domain.Form.TABLE_NAME)
@NoArgsConstructor
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

    Long creatorId;

    boolean shuffled;

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

    public Long getCreatorId() {
        return creatorId;
    }

    public Form(String name, String info, Long creatorId, boolean shuffled) {
        this.name = name;
        this.info = info;
        this.creatorId = creatorId;
        this.shuffled = shuffled;
    }

    public Form(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Form(List<Task> tasks, Long creatorId) {
        this.tasks = tasks;
        this.creatorId = creatorId;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addTasks(Collection<Task> tasks) {
        this.tasks.addAll(tasks);
    }
}

