package com.hse.units.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*
 * Placeholder for future `Task` class;
 * TODO: add different type of tasks
 * */

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String body;
    private String answer;
    private long author;

    protected Task() {
    }

    public Task(String title, String body, String answer, long author) {
        this.title = title;
        this.body = body;
        this.answer = answer;
        this.author = author;
    }

    //temp
    public Task(Long id, String title, String body, String answer, long author) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.answer = answer;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", answer='" + answer + '\'' +
                ", author=" + author +
                '}';
    }

    boolean checkCorrectness(String actual) {
        return actual.equals(answer);
    }
}