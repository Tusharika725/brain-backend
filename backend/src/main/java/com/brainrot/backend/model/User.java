package com.brainrot.backend.model;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String username;

    @ElementCollection
    private Map<String, GameResult> completedGames = new HashMap<>();

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public Long getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void saveGameScore(String gameName, int earnedPoints, int maxPoints) {
        completedGames.put(gameName, new GameResult(earnedPoints, maxPoints));
    }

    public int getBrainRotScore() {
        if (completedGames.isEmpty()) {
            return 0;
        }
        int totalEarned = 0;
        int totalMax = 0;

        for (GameResult result : completedGames.values()) {
            totalEarned += result.getPointsEarned();
            totalMax += result.getMaxPoints();
        }
        double percentage = ((double) totalEarned / totalMax) * 100;
        return (int) Math.round(percentage);
    }

}