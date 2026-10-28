package com.smartresume.ai.exception;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 7. EXCEPTION HANDLING (FILE I/O EXCEPTION WRAPPER)
 * ============================================================================
 * Wraps checked java.io.IOException into an unchecked custom application exception.
 * ============================================================================
 */
public class FileStorageException extends ResumeAppException {

    private final String filePath;

    public FileStorageException(String filePath, String message) {
        super(String.format("File operation failed on '%s': %s", filePath, message));
        this.filePath = filePath;
    }

    public FileStorageException(String filePath, String message, Throwable cause) {
        super(String.format("File operation failed on '%s': %s", filePath, message), cause);
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
