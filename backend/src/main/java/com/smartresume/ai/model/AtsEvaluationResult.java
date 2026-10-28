package com.smartresume.ai.model;

import java.util.*;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 8. COLLECTIONS (List, Set, Map implementation and operations)
 * 1. DATA TYPES (double, boolean, int)
 * 3. CLASSES & OBJECTS (Encapsulated state, business methods)
 * ============================================================================
 */
public class AtsEvaluationResult {

    // Primitive & Wrapper Data Types (Concept 1: Data Types)
    private double overallScore; // Out of 100.0
    private boolean passedAts;
    private int totalKeywordsMatched;

    private String grade; // e.g. "A+", "B", "C"
    private String summaryFeedback;

    // Collections Framework (Concept 8: Collections)
    // Map: category -> score (0-100)
    private Map<String, Integer> categoryScores;

    // List: ordered tips and strengths
    private List<String> strengths;
    private List<String> improvements;

    // Set: unique deduplicated keywords (TreeSet sorts alphabetically)
    private Set<String> detectedKeywords;
    private Set<String> recommendedKeywords;

    public AtsEvaluationResult() {
        this.categoryScores = new HashMap<>();
        this.strengths = new ArrayList<>();
        this.improvements = new ArrayList<>();
        this.detectedKeywords = new TreeSet<>();
        this.recommendedKeywords = new TreeSet<>();
    }

    public AtsEvaluationResult(double overallScore, String grade, String summaryFeedback) {
        this();
        this.overallScore = overallScore;
        this.grade = grade;
        this.summaryFeedback = summaryFeedback;
        this.passedAts = overallScore >= 70.0;
    }

    public void addCategoryScore(String category, int score) {
        this.categoryScores.put(category, score);
    }

    public void addStrength(String strength) {
        this.strengths.add(strength);
    }

    public void addImprovement(String tip) {
        this.improvements.add(tip);
    }

    // Getters and Setters
    public double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(double overallScore) {
        this.overallScore = overallScore;
        this.passedAts = overallScore >= 70.0;
    }

    public boolean isPassedAts() {
        return passedAts;
    }

    public void setPassedAts(boolean passedAts) {
        this.passedAts = passedAts;
    }

    public int getTotalKeywordsMatched() {
        return totalKeywordsMatched;
    }

    public void setTotalKeywordsMatched(int totalKeywordsMatched) {
        this.totalKeywordsMatched = totalKeywordsMatched;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSummaryFeedback() {
        return summaryFeedback;
    }

    public void setSummaryFeedback(String summaryFeedback) {
        this.summaryFeedback = summaryFeedback;
    }

    public Map<String, Integer> getCategoryScores() {
        return categoryScores;
    }

    public void setCategoryScores(Map<String, Integer> categoryScores) {
        this.categoryScores = categoryScores;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getImprovements() {
        return improvements;
    }

    public void setImprovements(List<String> improvements) {
        this.improvements = improvements;
    }

    public Set<String> getDetectedKeywords() {
        return detectedKeywords;
    }

    public void setDetectedKeywords(Set<String> detectedKeywords) {
        this.detectedKeywords = detectedKeywords;
    }

    public Set<String> getRecommendedKeywords() {
        return recommendedKeywords;
    }

    public void setRecommendedKeywords(Set<String> recommendedKeywords) {
        this.recommendedKeywords = recommendedKeywords;
    }
}
