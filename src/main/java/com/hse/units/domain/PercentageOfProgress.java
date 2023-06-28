package com.hse.units.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = PercentageOfProgress.TABLE_NAME)
@AllArgsConstructor
public class PercentageOfProgress {
    static public final String TABLE_NAME = "progress";

    private int solvedEasy = 0;
    private int solvedMedium = 0;
    private int solvedHard = 0;
    private int totalEasy = 0;
    private int totalMedium = 0;
    private int totalHard = 0;
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
    public void update(Task task, boolean wasSolved) {
        switch (task.getLevel()) {
            case EASY -> {
                totalEasy++;
                if (wasSolved) {
                    solvedEasy++;
                }
            }
            case MEDIUM -> {
                totalMedium++;
                if (wasSolved) {
                    solvedMedium++;
                }
            }
            case HARD -> {
                totalHard++;
                if (wasSolved) {
                    solvedHard++;
                }
            }
        }
    }

    public Double getPercent() {
        int solved = solvedEasy + solvedMedium + solvedHard;
        int total = totalEasy + totalMedium + totalHard;
        return calculatePercentage(solved, total);
    }

    public Double getPercentOfEasy() {
        return calculatePercentage(solvedEasy, totalEasy);
    }

    public Double getPercentOfMedium() {
        return calculatePercentage(solvedMedium, totalMedium);
    }

    public Double getPercentOfHard() {
        return calculatePercentage(solvedHard, totalHard);
    }

    private Double calculatePercentage(double solved, double total) {
        return solved * 100 / total;
    }

}

