package com.smartresume.ai.model;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 4. INHERITANCE (Extends ResumeSectionItem)
 * 1. DATA TYPES (double for cgpa, int for graduationYear)
 * 2. STRINGS (Parsing numerical values from string input)
 * ============================================================================
 */
public class EducationItem extends ResumeSectionItem {

    private String degree;
    private String institution;
    private String year;
    private String grade;

    // Numerical Data Types (Concept 1: Data Types)
    private double numericCgpa;
    private int graduationYear;

    public EducationItem() {
        super();
        this.numericCgpa = 0.0;
        this.graduationYear = 0;
    }

    public EducationItem(String degree, String institution, String year, String grade) {
        super(degree, institution);
        this.degree = degree;
        this.institution = institution;
        this.year = year;
        this.grade = grade;
        parseNumericalValues();
    }

    /**
     * Parses numerical primitives from String attributes
     */
    private void parseNumericalValues() {
        if (grade != null) {
            try {
                // Extracts float/double from "8.5 CGPA" or "85%"
                String clean = grade.replaceAll("[^0-9.]", " ").trim();
                if (!clean.isEmpty()) {
                    String[] tokens = clean.split("\\s+");
                    this.numericCgpa = Double.parseDouble(tokens[0]); // Type Conversion
                }
            } catch (NumberFormatException ignored) {}
        }
        if (year != null) {
            try {
                String clean = year.replaceAll("[^0-9]", " ").trim();
                if (!clean.isEmpty()) {
                    String[] tokens = clean.split("\\s+");
                    this.graduationYear = Integer.parseInt(tokens[tokens.length - 1]);
                }
            } catch (NumberFormatException ignored) {}
        }
    }

    @Override
    public String getDisplayTitle() {
        return String.format("%s - %s", degree, institution);
    }

    @Override
    public String toFormattedText() {
        return String.format("• %s | %s (%s) [Score: %s]", degree, institution, year, grade);
    }

    // Getters and Setters
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
        this.title = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
        this.description = institution;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
        parseNumericalValues();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        parseNumericalValues();
    }

    public double getNumericCgpa() {
        return numericCgpa;
    }

    public void setNumericCgpa(double numericCgpa) {
        this.numericCgpa = numericCgpa;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }
}
