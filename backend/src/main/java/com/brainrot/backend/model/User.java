package com.brainrot.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = true)
    private String username;
    @JsonIgnore
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private int totalAuraPoints = 0;

    // @ElementCollection
    // private Map<String, GameResult> completedGames = new HashMap<>();
    public User() {
    }

    public User(String email, String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.totalAuraPoints = 0;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getAuraPoints() {
        return this.totalAuraPoints;
    }

    public void addAuraPoints(int pointsToAdd) {
        this.totalAuraPoints += pointsToAdd;
    }


}