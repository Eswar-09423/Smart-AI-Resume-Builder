package com.smartresume.ai.service.impl;

import com.smartresume.ai.model.PersonalDetails;
import com.smartresume.ai.model.Resume;
import com.smartresume.ai.service.IResumeExporter;
import com.smartresume.ai.util.StringSanitizer;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 4. INHERITANCE (ABSTRACT CLASS IMPLEMENTING INTERFACE)
 * ============================================================================
 * Abstract base class providing common formatting utilities to all concrete
 * resume exporters. Subclasses override formatBody() to provide format-specific
 * representation (Markdown, TXT, HTML).
 * ============================================================================
 */
public abstract class AbstractResumeExporter implements IResumeExporter {

    @Override
    public String export(Resume resume) {
        if (resume == null) {
            return "";
        }
        return formatResume(resume);
    }

    /**
     * Template method pattern: delegates structure to formatResume
     */
    protected abstract String formatResume(Resume resume);

    /**
     * Shared helper to safely get contact string from PersonalDetails
     */
    protected String buildContactLine(PersonalDetails p) {
        if (p == null) return "";
        StringBuilder sb = new StringBuilder();
        if (p.getEmail() != null && !p.getEmail().isBlank()) sb.append(p.getEmail().trim());
        if (p.getPhone() != null && !p.getPhone().isBlank()) {
            if (sb.length() > 0) sb.append(" | ");
            sb.append(p.getPhone().trim());
        }
        if (p.getLocation() != null && !p.getLocation().isBlank()) {
            if (sb.length() > 0) sb.append(" | ");
            sb.append(p.getLocation().trim());
        }
        if (p.getLinkedin() != null && !p.getLinkedin().isBlank()) {
            if (sb.length() > 0) sb.append(" | ");
            sb.append(p.getLinkedin().trim());
        }
        if (p.getGithub() != null && !p.getGithub().isBlank()) {
            if (sb.length() > 0) sb.append(" | ");
            sb.append(p.getGithub().trim());
        }
        return sb.toString();
    }
}
