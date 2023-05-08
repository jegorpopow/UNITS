package com.hse.units.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private boolean standalone;

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

    public Task(String title, String body, String answer, long author, boolean standalone) {
        this.title = title;
        this.body = body;
        this.answer = answer;
        this.author = author;
        this.standalone = standalone;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tag_by_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TaskTag> tags = new HashSet<>();

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

    public void addTag(TaskTag tag) {
        tags.add(tag);
    }

    public Set<TaskTag> getTags() {
        return tags;
    }

    public boolean isStandalone() {
        return standalone;
    }
}
