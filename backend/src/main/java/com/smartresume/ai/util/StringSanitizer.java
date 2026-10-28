package com.smartresume.ai.util;

import java.util.regex.Pattern;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 2. STRINGS & 1. DATA TYPES
 * ============================================================================
 * Comprehensive utility showcasing Java String manipulation:
 * - String immutability & sanitization
 * - Regular Expressions (Pattern & Matcher) for Email/Phone/URL validation
 * - String trimming, case normalization, substring, and tokenization
 * - Primitive Data Types (int, boolean) for validation outcomes and length checks
 * ============================================================================
 */
public final class StringSanitizer {

    // Regex Patterns for validation (Concept 2: Strings)
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[+]?[0-9\\s\\-().]{7,20}$");

    private static final Pattern URL_PATTERN =
            Pattern.compile("^(https?://)?(www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/.*)?$");

    // Private constructor prevents instantiation (Utility Pattern)
    private StringSanitizer() {}

    /**
     * Sanitizes input string by trimming and preventing null pointers.
     */
    public static String safeTrim(String input) {
        if (input == null) {
            return "";
        }
        return input.trim();
    }

    /**
     * Sanitizes input with a fallback default value.
     */
    public static String safeTrim(String input, String defaultValue) {
        String trimmed = safeTrim(input);
        return trimmed.isEmpty() ? defaultValue : trimmed;
    }

    /**
     * Validates email address using Regex Pattern matching.
     * Demonstrates primitive 'boolean' data type return.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validates phone number format using Regex.
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Validates URL format (e.g. LinkedIn, GitHub, Portfolio).
     */
    public static boolean isValidUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }
        return URL_PATTERN.matcher(url.trim()).matches();
    }

    /**
     * Calculates word count using String split and regular expression whitespace tokenization.
     * Demonstrates primitive 'int' data type return.
     */
    public static int countWords(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");
        return words.length;
    }

    /**
     * Capitalizes first letter of each word (Title Case) using StringBuilder.
     */
    public static String toTitleCase(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }
        String[] words = text.trim().split("\\s+");
        StringBuilder sb = new StringBuilder(); // Efficient String building

        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase())
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }
}
