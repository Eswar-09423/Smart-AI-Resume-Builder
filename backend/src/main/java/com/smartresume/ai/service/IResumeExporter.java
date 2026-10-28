package com.smartresume.ai.service;

import com.smartresume.ai.model.Resume;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 5. INTERFACE
 * ============================================================================
 * Defines the contract for exporting a Resume domain model into various
 * document formats (Markdown, Plain Text, HTML).
 * ============================================================================
 */
public interface IResumeExporter {

    /**
     * Converts Resume object to formatted String content
     */
    String export(Resume resume);

    /**
     * Returns file extension (e.g. "md", "txt", "html")
     */
    String getFileExtension();

    /**
     * Returns MIME content type for HTTP responses
     */
    String getMimeType();
}
