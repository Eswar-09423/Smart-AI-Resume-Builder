package com.smartresume.ai.exception;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 7. EXCEPTION HANDLING (SUBCLASS EXCEPTION)
 * ============================================================================
 * Thrown when user-entered resume details violate format, length, or presence rules.
 * Extends ResumeAppException demonstrating multi-level exception inheritance.
 * ============================================================================
 */
public class ResumeValidationException extends ResumeAppException {

    private final String fieldName;

    public ResumeValidationException(String fieldName, String message) {
        super(String.format("Validation failed for field '%s': %s", fieldName, message));
        this.fieldName = fieldName;
    }

    public ResumeValidationException(String message) {
        super(message);
        this.fieldName = "GENERAL";
    }

    public String getFieldName() {
        return fieldName;
    }
}
