package com.smartresume.ai.exception;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 7. EXCEPTION HANDLING (BASE CUSTOM EXCEPTION)
 * ============================================================================
 * Custom Exception hierarchy rooted at ResumeAppException (extends RuntimeException).
 * Demonstrates inheritance within exception classes, constructor chaining with super(),
 * and standard error message propagation.
 * ============================================================================
 */
public class ResumeAppException extends RuntimeException {

    private final long errorTimestamp;

    public ResumeAppException(String message) {
        super(message);
        this.errorTimestamp = System.currentTimeMillis(); // OOP Concept: Data Types (long)
    }

    public ResumeAppException(String message, Throwable cause) {
        super(message, cause);
        this.errorTimestamp = System.currentTimeMillis();
    }

    public long getErrorTimestamp() {
        return errorTimestamp;
    }
}
