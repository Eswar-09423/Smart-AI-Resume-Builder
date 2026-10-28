package com.smartresume.ai.model;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 4. INHERITANCE (SUBCLASS EXTENDING BASE CLASS)
 * ============================================================================
 * ExperienceItem inherits from ResumeSectionItem using 'extends'.
 * Demonstrates:
 * - Reusing base class state (id, description, orderIndex)
 * - Constructor chaining via super()
 * - Method overriding (@Override)
 * - Specialized fields: company, role, duration, isCurrentRole
 * ============================================================================
 */
public class ExperienceItem extends ResumeSectionItem {

    // Subclass specific fields
    private String role;
    private String company;
    private String duration;
    private boolean currentRole; // Primitive boolean (Concept 1: Data Types)

    public ExperienceItem() {
        super();
    }

    public ExperienceItem(String role, String company, String duration, String description) {
        super(role, description); // Call to superclass constructor
        this.role = role;
        this.company = company;
        this.duration = duration;
        this.currentRole = duration != null && duration.toLowerCase().contains("present");
    }

    // Method Overriding (Concept 4: Inheritance & Polymorphism)
    @Override
    public String getDisplayTitle() {
        return String.format("%s at %s", (role != null ? role : "Role"), (company != null ? company : "Company"));
    }

    @Override
    public String toFormattedText() {
        return String.format("• %s | %s (%s)\n  %s", role, company, duration, description);
    }

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
        this.title = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
        this.currentRole = duration != null && duration.toLowerCase().contains("present");
    }

    public boolean isCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(boolean currentRole) {
        this.currentRole = currentRole;
    }
}
