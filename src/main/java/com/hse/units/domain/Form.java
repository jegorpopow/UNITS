package com.hse.units.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = com.hse.units.domain.Form.TABLE_NAME)
public class Form {
    public static final String TABLE_NAME = "forms";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "form_contains", joinColumns = @JoinColumn(name = "id"))
    private Set<Long> tasks = new HashSet<>();

    String name;

    String info;

    Long creator;

    protected Form() {
    }

    public Long getId() {
        return id;
    }

    public Set<Long> getTasks() {
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
}

