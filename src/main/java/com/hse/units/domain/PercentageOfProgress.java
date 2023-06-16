package com.hse.units.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = PercentageOfProgress.TABLE_NAME)
@AllArgsConstructor
public class PercentageOfProgress {
    static public final String TABLE_NAME = "progress";

    private int easy = 0;
    private int medium = 0;
    private int hard = 0;
    @Id
    @GeneratedValue
    private Long id;

    public PercentageOfProgress() {

    }

    public PercentageOfProgress(TaskTag tag, User user) {
        this.tag = tag;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @ManyToOne
    private TaskTag tag;

    @ManyToOne
    private User user;

    public int getEasy() {
        return easy;
    }

    public int getMedium() {
        return medium;
    }

    public int getHard() {
        return hard;
    }

    public TaskTag getTag() {
        return tag;
    }

    public User getUser() {
        return user;
    }
}

