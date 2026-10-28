package com.smartresume.ai.model;

import com.smartresume.ai.exception.ResumeValidationException;

import java.util.*;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 3. CLASSES & OBJECTS (Main domain model / Aggregate Root)
 * 5. INTERFACE (Implements IValidatable)
 * 8. COLLECTIONS (List, Set, Map usage)
 * 1. DATA TYPES (double, int, long, boolean)
 * 2. STRINGS (Data manipulation & formatting)
 * ============================================================================
 */
public class Resume implements IValidatable {

    private String id;
    private String title;
    private long lastUpdated;

    // Composition with PersonalDetails (Classes & Objects)
    private PersonalDetails personalDetails;

    // Collections Framework (Concept 8: Collections)
    // List preserves ordering of items
    private List<ExperienceItem> experiences;
    private List<ProjectItem> projects;
    private List<EducationItem> educations;
    private List<CertificationItem> certifications;
    private List<AchievementItem> achievements;

    // Set guarantees unique skills (no duplicate skill entries)
    private Set<String> skills;
    private String technicalSkillsRaw;

    // Map stores dynamic theme, layout, and style configuration
    private Map<String, String> stylePreferences;

    // Primitives (Concept 1: Data Types)
    private int resumeScore;
    private double completenessPercentage;

    public Resume() {
        this.id = UUID.randomUUID().toString();
        this.lastUpdated = System.currentTimeMillis();
        this.personalDetails = new PersonalDetails();
        this.experiences = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.educations = new ArrayList<>();
        this.certifications = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.skills = new TreeSet<>(String.CASE_INSENSITIVE_ORDER); // Sorted unique set
        this.stylePreferences = new HashMap<>();

        // Default style preferences
        this.stylePreferences.put("theme", "modern");
        this.stylePreferences.put("layout", "single");
    }

    /**
     * Interface contract implementation (Concept 5: Interface)
     */
    @Override
    public void validate() throws ResumeValidationException {
        if (personalDetails == null) {
            throw new ResumeValidationException("personalDetails", "Personal details section is required.");
        }
        personalDetails.validate(); // Propagates validation
    }

    /**
     * Adds skills from comma-separated string to the unique Set
     */
    public void setTechnicalSkillsRaw(String technicalSkillsRaw) {
        this.technicalSkillsRaw = technicalSkillsRaw;
        this.skills.clear();
        if (technicalSkillsRaw != null && !technicalSkillsRaw.isBlank()) {
            String[] tokens = technicalSkillsRaw.split(",");
            for (String token : tokens) {
                String clean = token.trim();
                if (!clean.isEmpty()) {
                    this.skills.add(clean);
                }
            }
        }
    }

    /**
     * Calculates resume completeness percentage (Concept 1: Data Types - double calculation)
     */
    public double calculateCompleteness() {
        int totalSections = 6;
        int completedSections = 0;

        if (personalDetails != null && personalDetails.getFullName() != null && !personalDetails.getFullName().isBlank()) {
            completedSections++;
        }
        if (personalDetails != null && personalDetails.getSummary() != null && !personalDetails.getSummary().isBlank()) {
            completedSections++;
        }
        if (!experiences.isEmpty()) completedSections++;
        if (!projects.isEmpty()) completedSections++;
        if (!educations.isEmpty()) completedSections++;
        if (!skills.isEmpty()) completedSections++;

        this.completenessPercentage = (double) completedSections / totalSections * 100.0;
        return this.completenessPercentage;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public List<ExperienceItem> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperienceItem> experiences) {
        this.experiences = experiences;
    }

    public List<ProjectItem> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectItem> projects) {
        this.projects = projects;
    }

    public List<EducationItem> getEducations() {
        return educations;
    }

    public void setEducations(List<EducationItem> educations) {
        this.educations = educations;
    }

    public List<CertificationItem> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<CertificationItem> certifications) {
        this.certifications = certifications;
    }

    public List<AchievementItem> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<AchievementItem> achievements) {
        this.achievements = achievements;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }

    public String getTechnicalSkillsRaw() {
        return technicalSkillsRaw;
    }

    public Map<String, String> getStylePreferences() {
        return stylePreferences;
    }

    public void setStylePreferences(Map<String, String> stylePreferences) {
        this.stylePreferences = stylePreferences;
    }

    public int getResumeScore() {
        return resumeScore;
    }

    public void setResumeScore(int resumeScore) {
        this.resumeScore = resumeScore;
    }

    public double getCompletenessPercentage() {
        return completenessPercentage;
    }

    public void setCompletenessPercentage(double completenessPercentage) {
        this.completenessPercentage = completenessPercentage;
    }
}
