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

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean getResult() {
        return result;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Answer(User user, Task task, String answer) {
        this.user = user;
        this.task = task;
        this.answer = answer;
        this.timestamp = new Date();
        this.result = task.checkCorrectness(answer);
    }
}

