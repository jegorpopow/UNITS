package com.hse.units.domain;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = com.hse.units.domain.Answer.TABLE_NAME)
public class Answer {
    public static final String TABLE_NAME = "answers";

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

    @ManyToOne
    User user;

    @ManyToOne
    Task task;

    String answer;

    boolean result;

    @Temporal(TemporalType.TIMESTAMP)
    Date timestamp;

    protected Answer() {
    }
}

