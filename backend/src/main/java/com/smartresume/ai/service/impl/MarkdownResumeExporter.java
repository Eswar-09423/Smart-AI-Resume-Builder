package com.smartresume.ai.service.impl;

import com.smartresume.ai.model.*;
import org.springframework.stereotype.Component;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 4. INHERITANCE (Extends AbstractResumeExporter)
 * 2. STRINGS (Heavy use of StringBuilder for efficient document generation)
 * 8. COLLECTIONS (Iterating over List and Set collections)
 * ============================================================================
 */
@Component("markdownExporter")
public class MarkdownResumeExporter extends AbstractResumeExporter {

    @Override
    public String getFileExtension() {
        return "md";
    }

    @Override
    public String getMimeType() {
        return "text/markdown";
    }

    @Override
    protected String formatResume(Resume resume) {
        StringBuilder sb = new StringBuilder(); // Efficient String generation (Concept 2: Strings)
        PersonalDetails p = resume.getPersonalDetails();

        // Header
        if (p != null) {
            sb.append("# ").append(p.getFullName() != null ? p.getFullName() : "Resume").append("\n\n");
            sb.append("**").append(buildContactLine(p)).append("**\n\n");
            sb.append("---\n\n");

            // Summary
            if (p.getSummary() != null && !p.getSummary().isBlank()) {
                sb.append("## Professional Summary\n\n");
                sb.append(p.getSummary().trim()).append("\n\n");
            }
        }

        // Experience (Concept 8: Collections)
        if (resume.getExperiences() != null && !resume.getExperiences().isEmpty()) {
            sb.append("## Work Experience\n\n");
            for (ExperienceItem item : resume.getExperiences()) {
                sb.append("### ").append(item.getRole()).append(" - ").append(item.getCompany()).append("\n");
                sb.append("*").append(item.getDuration()).append("*\n\n");
                sb.append(item.getDescription()).append("\n\n");
            }
        }

        // Projects
        if (resume.getProjects() != null && !resume.getProjects().isEmpty()) {
            sb.append("## Projects\n\n");
            for (ProjectItem item : resume.getProjects()) {
                sb.append("### ").append(item.getTitle()).append("\n");
                if (item.getTechnologies() != null && !item.getTechnologies().isBlank()) {
                    sb.append("**Technologies:** ").append(item.getTechnologies()).append("\n\n");
                }
                sb.append(item.getDescription()).append("\n\n");
            }
        }

        // Education
        if (resume.getEducations() != null && !resume.getEducations().isEmpty()) {
            sb.append("## Education\n\n");
            for (EducationItem item : resume.getEducations()) {
                sb.append("### ").append(item.getDegree()).append(" - ").append(item.getInstitution()).append("\n");
                sb.append("*").append(item.getYear()).append(" | ").append(item.getGrade()).append("*\n\n");
            }
        }

        // Technical Skills
        if (resume.getSkills() != null && !resume.getSkills().isEmpty()) {
            sb.append("## Technical Skills\n\n");
            sb.append(String.join(", ", resume.getSkills())).append("\n\n");
        }

        // Certifications
        if (resume.getCertifications() != null && !resume.getCertifications().isEmpty()) {
            sb.append("## Certifications\n\n");
            for (CertificationItem item : resume.getCertifications()) {
                sb.append("- **").append(item.getName()).append("** - ").append(item.getOrganization());
                if (item.getYear() != null && !item.getYear().isBlank()) {
                    sb.append(" (").append(item.getYear()).append(")");
                }
                sb.append("\n");
            }
            sb.append("\n");
        }

        // Achievements
        if (resume.getAchievements() != null && !resume.getAchievements().isEmpty()) {
            sb.append("## Achievements\n\n");
            for (AchievementItem item : resume.getAchievements()) {
                sb.append("- ").append(item.getAchievementText()).append("\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
