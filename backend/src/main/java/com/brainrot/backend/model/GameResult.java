package com.brainrot.backend.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class GameResult {
    private int pointsEarned;
    private int maxPoints;

    public GameResult() {

    }
    public GameResult(int pointsEarned,int maxPoints){
        this.maxPoints = maxPoints;
        this.pointsEarned = pointsEarned;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

}
