package com.smartresume.ai.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 4. INHERITANCE (Extends ResumeSectionItem)
 * 8. COLLECTIONS (List<String> for technologies)
 * 1. DATA TYPES (int for teamSize)
 * ============================================================================
 */
public class ProjectItem extends ResumeSectionItem {

    private String technologies;
    private String projectUrl;
    private int teamSize; // Primitive int (Concept 1: Data Types)

    public ProjectItem() {
        super();
        this.teamSize = 1;
    }

    public ProjectItem(String title, String technologies, String description) {
        super(title, description); // super() constructor call
        this.technologies = technologies;
        this.teamSize = 1;
    }

    @Override
    public String getDisplayTitle() {
        return (title != null && !title.isBlank()) ? title : "Project";
    }

    @Override
    public String toFormattedText() {
        return String.format("• %s [Tech: %s]\n  %s", title, technologies, description);
    }

    /**
     * Splits comma-separated technologies into a Collection List (Concept 8: Collections)
     */
    public List<String> getTechnologyList() {
        List<String> list = new ArrayList<>();
        if (technologies != null && !technologies.isBlank()) {
            String[] parts = technologies.split(",");
            for (String part : parts) {
                String clean = part.trim();
                if (!clean.isEmpty()) {
                    list.add(clean);
                }
            }
        }
        return list;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }
}
