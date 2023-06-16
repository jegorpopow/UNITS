package com.hse.units.domain;

import java.util.HashMap;

public class UserProgress {
    private int solvedEasyTasks = 0;
    private int solvedMediumTasks = 0;
    private int solvedHardTasks = 0;
    private int totalEasyTasks = 0;
    private int totalMediumTasks = 0;
    private int totalHardTasks = 0;

    public void update(Task task, boolean wasSolved) {
        switch (task.getLevel()) {
            case EASY -> {
                totalEasyTasks++;
                if (wasSolved) {
                    solvedEasyTasks++;
                }
            }
            case MEDIUM -> {
                totalMediumTasks++;
                if (wasSolved) {
                    solvedMediumTasks++;
                }
            }
            case HARD -> {
                totalHardTasks++;
                if (wasSolved) {
                    solvedHardTasks++;
                }
            }
        }
    }

    public double getPercent() {
        int solved = solvedEasyTasks + solvedMediumTasks + solvedHardTasks;
        int total = totalEasyTasks + totalMediumTasks + totalHardTasks;
        return calculatePercentage(solved, total);
    }

    public double getPercentOfEasy() {
        return calculatePercentage(solvedEasyTasks, totalEasyTasks);
    }

    public double getPercentOfMedium() {
        return calculatePercentage(solvedMediumTasks, totalMediumTasks);
    }

    public double getPercentOfHard() {
        return calculatePercentage(solvedHardTasks, totalHardTasks);
    }

    private double calculatePercentage(double solved, double total) {
        return solved * 100 / total;
    }

}
