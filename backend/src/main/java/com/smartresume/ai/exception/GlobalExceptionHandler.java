package com.smartresume.ai.exception;

import com.smartresume.ai.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 7. EXCEPTION HANDLING (CENTRALIZED CONTROLLER ADVICE)
 * ============================================================================
 * Catches checked and unchecked exceptions thrown from any controller/service layer,
 * maps them to appropriate HTTP status codes, and returns unified ApiResponse<Void>.
 * ============================================================================
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResumeValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(ResumeValidationException ex) {
        System.err.println("[VALIDATION EXCEPTION] " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(ResourceNotFoundException ex) {
        System.err.println("[NOT FOUND EXCEPTION] " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(404, ex.getMessage()));
    }

    @ExceptionHandler(AiServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleAiServiceException(AiServiceException ex) {
        System.err.println("[AI SERVICE EXCEPTION] " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponse.error(503, ex.getMessage()));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiResponse<Void>> handleFileStorageException(FileStorageException ex) {
        System.err.println("[FILE STORAGE EXCEPTION] " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        System.err.println("[UNEXPECTED EXCEPTION] " + ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "Internal server error: " + ex.getMessage()));
    }
}
