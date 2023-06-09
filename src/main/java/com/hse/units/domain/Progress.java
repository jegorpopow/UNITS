package com.hse.units.domain;

import java.util.HashMap;



public class Progress {
    private static class percentageOfProgress {
        int easy;
        int medium;
        int hard;
    }
    HashMap<TaskTag, percentageOfProgress> progress;
}
