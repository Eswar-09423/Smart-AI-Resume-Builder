package com.smartresume.ai.model;

import com.smartresume.ai.exception.ResumeValidationException;
import com.smartresume.ai.util.StringSanitizer;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 3. CLASSES & OBJECTS (Encapsulation, constructors, instance state)
 * 1. DATA TYPES (Primitives: boolean, int)
 * 2. STRINGS (Validation, sanitization, regex matching)
 * 5. INTERFACE (Implements IValidatable)
 * ============================================================================
 */
public class PersonalDetails implements IValidatable {

    // String fields (Concept 2: Strings)
    private String fullName;
    private String email;
    private String phone;
    private String location;
    private String linkedin;
    private String github;
    private String careerGoal;
    private String summary;

    // Default constructor
    public PersonalDetails() {}

    // Parameterized constructor
    public PersonalDetails(String fullName, String email, String phone, String location) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.location = location;
    }

    /**
     * Interface implementation for validation (Concept 5: Interface)
     * Throws custom exception if validation fails (Concept 7: Exception Handling)
     */
    @Override
    public void validate() throws ResumeValidationException {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new ResumeValidationException("fullName", "Full name is required.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ResumeValidationException("email", "Email address is required.");
        }
        if (!StringSanitizer.isValidEmail(email)) {
            throw new ResumeValidationException("email", "Email format is invalid: " + email);
        }
    }

    /**
     * Checks if LinkedIn profile is provided (Concept 1: Data Types - boolean return)
     */
    public boolean hasLinkedin() {
        return linkedin != null && !linkedin.isBlank();
    }

    /**
     * Calculates summary length in words (Concept 1: Data Types - int return)
     */
    public int getSummaryWordCount() {
        return StringSanitizer.countWords(summary);
    }

    // Getters and Setters (Encapsulation)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getCareerGoal() {
        return careerGoal;
    }

    public void setCareerGoal(String careerGoal) {
        this.careerGoal = careerGoal;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return String.format("%s <%s> | %s | %s", fullName, email, phone, location);
    }
}
