package com.brainrot.backend.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"feedback", "creativity", "grammar", "relevance", "syntax", "vocabulary", "auraGained"})
public class CaptionResponseDto {
    private int vocabulary;  // Out of 10
    private int grammar;     // Out of 10
    private int creativity;  // Out of 15
    private int relevance;   // Out of 15
    private int syntax;      // Out of 10
    private int auraGained;      // Out of 60
    private String feedback;
    private int totalAuraPoints;
}
