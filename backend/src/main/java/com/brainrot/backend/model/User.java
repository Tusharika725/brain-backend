package com.brainrot.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    private String username;
    private int wordSearchScore;
    private int captionScore;

    public User() {

    }

    public User(String username) {
        this.username = username;
        this.wordSearchScore = 0;
        this.captionScore = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWordSearchScore() {
        return wordSearchScore;
    }

    public void setWordSearchScore(int wordSearchScore) {
        this.wordSearchScore = wordSearchScore;
    }

    public int getCaptionScore() {
        return captionScore;
    }

    public void setCaptionScore(int captionScore) {
        this.captionScore = captionScore;
    }

    public int getBrainRotScore() {
        return this.wordSearchScore + this.captionScore;
    }
}