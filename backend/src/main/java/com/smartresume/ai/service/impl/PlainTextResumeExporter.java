package com.smartresume.ai.service.impl;

import com.smartresume.ai.model.*;
import org.springframework.stereotype.Component;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 4. INHERITANCE (Extends AbstractResumeExporter)
 * 2. STRINGS (StringBuilder text layout)
 * ============================================================================
 */
@Component("plainTextExporter")
public class PlainTextResumeExporter extends AbstractResumeExporter {

    @Override
    public String getFileExtension() {
        return "txt";
    }

    @Override
    public String getMimeType() {
        return "text/plain";
    }

    @Override
    protected String formatResume(Resume resume) {
        StringBuilder sb = new StringBuilder();
        PersonalDetails p = resume.getPersonalDetails();

        // Header
        if (p != null) {
            sb.append("============================================================\n");
            sb.append(p.getFullName() != null ? p.getFullName().toUpperCase() : "RESUME").append("\n");
            sb.append(buildContactLine(p)).append("\n");
            sb.append("============================================================\n\n");

            if (p.getSummary() != null && !p.getSummary().isBlank()) {
                sb.append("PROFESSIONAL SUMMARY\n");
                sb.append("------------------------------------------------------------\n");
                sb.append(p.getSummary().trim()).append("\n\n");
            }
        }

        // Experience
        if (resume.getExperiences() != null && !resume.getExperiences().isEmpty()) {
            sb.append("WORK EXPERIENCE\n");
            sb.append("------------------------------------------------------------\n");
            for (ExperienceItem item : resume.getExperiences()) {
                sb.append(item.toFormattedText()).append("\n\n");
            }
        }

        // Projects
        if (resume.getProjects() != null && !resume.getProjects().isEmpty()) {
            sb.append("PROJECTS\n");
            sb.append("------------------------------------------------------------\n");
            for (ProjectItem item : resume.getProjects()) {
                sb.append(item.toFormattedText()).append("\n\n");
            }
        }

        // Education
        if (resume.getEducations() != null && !resume.getEducations().isEmpty()) {
            sb.append("EDUCATION\n");
            sb.append("------------------------------------------------------------\n");
            for (EducationItem item : resume.getEducations()) {
                sb.append(item.toFormattedText()).append("\n\n");
            }
        }

        // Skills
        if (resume.getSkills() != null && !resume.getSkills().isEmpty()) {
            sb.append("TECHNICAL SKILLS\n");
            sb.append("------------------------------------------------------------\n");
            sb.append(String.join(", ", resume.getSkills())).append("\n\n");
        }

        // Certifications
        if (resume.getCertifications() != null && !resume.getCertifications().isEmpty()) {
            sb.append("CERTIFICATIONS\n");
            sb.append("------------------------------------------------------------\n");
            for (CertificationItem item : resume.getCertifications()) {
                sb.append(item.toFormattedText()).append("\n");
            }
            sb.append("\n");
        }

        // Achievements
        if (resume.getAchievements() != null && !resume.getAchievements().isEmpty()) {
            sb.append("ACHIEVEMENTS\n");
            sb.append("------------------------------------------------------------\n");
            for (AchievementItem item : resume.getAchievements()) {
                sb.append(item.toFormattedText()).append("\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
