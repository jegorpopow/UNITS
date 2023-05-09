package com.hse.units.domain;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = com.hse.units.domain.FormResponse.TABLE_NAME)
public class FormResponse {
    public static final String TABLE_NAME = "form_responses";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinTable(
            name = "answers_by_response",
            joinColumns = @JoinColumn(name = "response_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne
    User user;

    @ManyToOne
    Form form;

    @Temporal(TemporalType.TIMESTAMP)
    Date timestamp;

    protected FormResponse() {
    }

    public FormResponse(User user, Form form) {
        this.user = user;
        this.form = form;
        this.timestamp = new Date();
    }

    public void addAnswer(Answer answer) {
        if (!form.getTasks().contains(answer.task)) {
            throw new RuntimeException();
        }

        answers.add(answer);
    }

    public long getId() {
        return id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public User getUser() {
        return user;
    }

    public Form getForm() {
        return form;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

