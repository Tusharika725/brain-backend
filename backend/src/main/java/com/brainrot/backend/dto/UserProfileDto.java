package com.brainrot.backend.dto;

public class UserProfileDto {
    private String username;
    private int totalAuraPoints;

    public UserProfileDto() {
    }

    public UserProfileDto(String username, int totalAuraPoints) {
        this.username = username;
        this.totalAuraPoints = totalAuraPoints;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalAuraPoints() {
        return totalAuraPoints;
    }

    public void setTotalAuraPoints(int totalAuraPoints) {
        this.totalAuraPoints = totalAuraPoints;
    }
}