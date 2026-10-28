package com.smartresume.ai.exception;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 7. EXCEPTION HANDLING
 * ============================================================================
 * Thrown when requested resume file or resource is not found on disk.
 * ============================================================================
 */
public class ResourceNotFoundException extends ResumeAppException {

    public ResourceNotFoundException(String resourceName, String identifier) {
        super(String.format("Resource '%s' with identifier '%s' was not found.", resourceName, identifier));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
