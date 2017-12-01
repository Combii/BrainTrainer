package com.combii.braintrainer;

import android.support.annotation.NonNull;


/**
 * Created by :0) on 28/11/2017.
 */

public class HighScore implements Comparable<HighScore>{

    private int score;
    private Difficulty difficulty;

    public HighScore(int score, Difficulty difficulty) {
        this.score = score;
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "HighScore{" +
                "score=" + score +
                ", difficulty=" + difficulty +
                '}';
    }

    @Override
    public int compareTo(@NonNull HighScore o) {
        if(this.getScore() > o.getScore()) return 1;
        else if(this.getScore() == o.getScore()) return 0;
        else return -1;
    }
}
