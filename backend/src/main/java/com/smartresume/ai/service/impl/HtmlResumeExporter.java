package com.smartresume.ai.service.impl;

import com.smartresume.ai.model.*;
import org.springframework.stereotype.Component;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 4. INHERITANCE (Extends AbstractResumeExporter)
 * 2. STRINGS (HTML Document generation)
 * 8. COLLECTIONS (Iteration over collections)
 * ============================================================================
 */
@Component("htmlExporter")
public class HtmlResumeExporter extends AbstractResumeExporter {

    @Override
    public String getFileExtension() {
        return "html";
    }

    @Override
    public String getMimeType() {
        return "text/html";
    }

    @Override
    protected String formatResume(Resume resume) {
        StringBuilder sb = new StringBuilder();
        PersonalDetails p = resume.getPersonalDetails();
        String theme = (resume.getStylePreferences() != null)
                ? resume.getStylePreferences().getOrDefault("theme", "modern")
                : "modern";

        sb.append("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n");
        sb.append("<meta charset=\"UTF-8\">\n<title>")
          .append(p != null && p.getFullName() != null ? p.getFullName() : "Resume")
          .append(" - Smart AI Resume</title>\n");
        sb.append("<style>\n");
        sb.append("body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; margin: 40px auto; max-width: 800px; }\n");
        sb.append(".header { text-align: center; border-bottom: 2px solid #2563eb; padding-bottom: 15px; margin-bottom: 20px; }\n");
        sb.append(".header h1 { margin: 0; color: #1e3a8a; }\n");
        sb.append(".contact { color: #64748b; font-size: 14px; margin-top: 5px; }\n");
        sb.append("h2 { color: #2563eb; border-bottom: 1px solid #e2e8f0; padding-bottom: 5px; margin-top: 25px; }\n");
        sb.append(".item { margin-bottom: 15px; }\n");
        sb.append(".item-title { font-weight: bold; color: #0f172a; }\n");
        sb.append(".item-sub { color: #2563eb; font-size: 13px; font-weight: 600; }\n");
        sb.append(".skill-tag { display: inline-block; background: #eff6ff; color: #1d4ed8; padding: 4px 10px; border-radius: 12px; margin: 3px; font-size: 12px; font-weight: bold; }\n");
        sb.append("</style>\n</head>\n<body>\n");

        if (p != null) {
            sb.append("<div class=\"header\">\n");
            sb.append("<h1>").append(escape(p.getFullName())).append("</h1>\n");
            sb.append("<div class=\"contact\">").append(escape(buildContactLine(p))).append("</div>\n");
            sb.append("</div>\n");

            if (p.getSummary() != null && !p.getSummary().isBlank()) {
                sb.append("<h2>Professional Summary</h2>\n");
                sb.append("<p>").append(escape(p.getSummary())).append("</p>\n");
            }
        }

        if (resume.getExperiences() != null && !resume.getExperiences().isEmpty()) {
            sb.append("<h2>Work Experience</h2>\n");
            for (ExperienceItem item : resume.getExperiences()) {
                sb.append("<div class=\"item\">\n");
                sb.append("<div class=\"item-title\">").append(escape(item.getRole())).append(" - ").append(escape(item.getCompany())).append("</div>\n");
                sb.append("<div class=\"item-sub\">").append(escape(item.getDuration())).append("</div>\n");
                sb.append("<p>").append(escape(item.getDescription())).append("</p>\n");
                sb.append("</div>\n");
            }
        }

        if (resume.getProjects() != null && !resume.getProjects().isEmpty()) {
            sb.append("<h2>Projects</h2>\n");
            for (ProjectItem item : resume.getProjects()) {
                sb.append("<div class=\"item\">\n");
                sb.append("<div class=\"item-title\">").append(escape(item.getTitle())).append("</div>\n");
                if (item.getTechnologies() != null && !item.getTechnologies().isBlank()) {
                    sb.append("<div class=\"item-sub\">Technologies: ").append(escape(item.getTechnologies())).append("</div>\n");
                }
                sb.append("<p>").append(escape(item.getDescription())).append("</p>\n");
                sb.append("</div>\n");
            }
        }

        if (resume.getEducations() != null && !resume.getEducations().isEmpty()) {
            sb.append("<h2>Education</h2>\n");
            for (EducationItem item : resume.getEducations()) {
                sb.append("<div class=\"item\">\n");
                sb.append("<div class=\"item-title\">").append(escape(item.getDegree())).append(" - ").append(escape(item.getInstitution())).append("</div>\n");
                sb.append("<div class=\"item-sub\">").append(escape(item.getYear())).append(" | ").append(escape(item.getGrade())).append("</div>\n");
                sb.append("</div>\n");
            }
        }

        if (resume.getSkills() != null && !resume.getSkills().isEmpty()) {
            sb.append("<h2>Technical Skills</h2>\n<div>\n");
            for (String skill : resume.getSkills()) {
                sb.append("<span class=\"skill-tag\">").append(escape(skill)).append("</span>\n");
            }
            sb.append("</div>\n");
        }

        sb.append("</body>\n</html>");
        return sb.toString();
    }

    private String escape(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;");
    }
}
