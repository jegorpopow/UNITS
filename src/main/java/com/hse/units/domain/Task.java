package com.hse.units.domain;

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


    @Override
    public boolean equals(Object obj) {
        return obj instanceof Task && ((Task) obj).id == id;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    public Task(String title, String body, String answer, long author) {
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

    public boolean checkCorrectness(String receivedAnswer) {
        return receivedAnswer != null && receivedAnswer.equals(answer);
    }
}
