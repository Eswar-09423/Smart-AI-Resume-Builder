package com.smartresume.ai.util;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 9. GENERICS & 1. DATA TYPES & 2. STRINGS
 * ============================================================================
 * Generic container class parameterized by type <T>.
 * Allows uniform API responses across the entire application regardless
 * of whether T is Resume, AtsEvaluationResult, List<String>, or String.
 * ============================================================================
 * @param <T> The payload data type
 */
public class ApiResponse<T> {

    // Primitive & Wrapper Data Types (Concept 1: Data Types)
    private final boolean success;
    private final int statusCode;
    private final long timestamp;

    // String manipulation (Concept 2: Strings)
    private final String message;

    // Generic payload type (Concept 9: Generics)
    private final T data;

    // Private constructor enforcing factory method usage (Concept 3: Encapsulation)
    private ApiResponse(boolean success, int statusCode, String message, T data) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = (message != null) ? message.trim() : "";
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Generic static factory method for successful responses
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, 200, message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Operation completed successfully");
    }

    /**
     * Generic static factory method for error responses
     */
    public static <T> ApiResponse<T> error(int statusCode, String message) {
        return new ApiResponse<>(false, statusCode, message, null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return error(400, message);
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("ApiResponse[success=%b, statusCode=%d, message='%s', timestamp=%d]",
                success, statusCode, message, timestamp);
    }
}
