package com.brainrot.backend.dto;

public class WordSearchResponseDto {

    private int auraGained;
    private int totalAuraPoints;

    public WordSearchResponseDto(int auraGained, int totalAuraPoints) {
        this.auraGained = auraGained;
        this.totalAuraPoints = totalAuraPoints;
    }

    public int getAuraGained() {
        return auraGained;
    }

    public void setAuraGained(int auraGained) {
        this.auraGained = auraGained;
    }

    public int getTotalAuraPoints() {
        return totalAuraPoints;
    }

    public void setTotalAuraPoints(int totalAuraPoints) {
        this.totalAuraPoints = totalAuraPoints;
    }
}