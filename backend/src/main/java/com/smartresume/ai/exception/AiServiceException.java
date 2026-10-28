package com.smartresume.ai.exception;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 7. EXCEPTION HANDLING (AI SERVICE EXCEPTION)
 * ============================================================================
 * Thrown when AI communication encounters API limits, missing keys, or timeouts.
 * ============================================================================
 */
public class AiServiceException extends ResumeAppException {

    private final String provider;

    public AiServiceException(String provider, String message) {
        super(String.format("AI Engine [%s] encountered error: %s", provider, message));
        this.provider = provider;
    }

    public AiServiceException(String provider, String message, Throwable cause) {
        super(String.format("AI Engine [%s] encountered error: %s", provider, message), cause);
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }
}
