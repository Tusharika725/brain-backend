package com.brainrot.backend.dto;

public class CaptionResponseDto {
    private int vocabulary;  // Out of 10
    private int grammar;     // Out of 10
    private int creativity;  // Out of 15
    private int relevance;   // Out of 15
    private int syntax;      // Out of 10

    private int totalPoints;      // Out of 60
    private String feedback;      // The AI's roast/explanation
    private int newBrainRotScore; // Their new global percentage out of 100

    public CaptionResponseDto(int vocabulary, int grammar, int creativity, int relevance, int syntax, int totalPoints, String feedback, int newBrainRotScore) {
        this.vocabulary = vocabulary;
        this.grammar = grammar;
        this.creativity = creativity;
        this.relevance = relevance;
        this.syntax = syntax;
        this.totalPoints = totalPoints;
        this.feedback = feedback;
        this.newBrainRotScore = newBrainRotScore;
    }
    public int getVocabulary() { return vocabulary; }
    public int getGrammar() { return grammar; }
    public int getCreativity() { return creativity; }
    public int getRelevance() { return relevance; }
    public int getSyntax() { return syntax; }
    public int getTotalPoints() { return totalPoints; }
    public String getFeedback() { return feedback; }
    public int getNewBrainRotScore() { return newBrainRotScore; }
}
