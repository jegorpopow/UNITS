package com.hse.units.domain;

import jakarta.persistence.*;

@Entity
@Table(name = com.hse.units.domain.TaskTag.TABLE_NAME)
public class TaskTag {
    public static final String TABLE_NAME = "task_tags";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String name;

    String info;

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TaskTag && ((TaskTag) obj).id == id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public TaskTag(String name, String about) {
        this.name = name;
        this.info = about;
    }

    protected TaskTag() {
    }

}
