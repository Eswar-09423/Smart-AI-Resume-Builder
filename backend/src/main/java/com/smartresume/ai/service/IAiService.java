package com.smartresume.ai.service;

import com.smartresume.ai.exception.AiServiceException;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 5. INTERFACE & 11. AI INTEGRATION
 * ============================================================================
 * Defines the contract for AI text generation engines.
 * Implementations can use Cloud LLMs (Gemini, OpenAI) or Local Heuristic AI.
 * ============================================================================
 */
public interface IAiService {

    /**
     * Sends prompt to the AI provider and returns enhanced content.
     *
     * @param prompt The prompt instruction for the AI.
     * @return Generated text content from AI.
     * @throws AiServiceException if the AI engine fails or encounters network issues.
     */
    String generateContent(String prompt) throws AiServiceException;

    /**
     * Checks if this AI provider is online and available.
     */
    boolean isAvailable();

    /**
     * Returns name of current AI engine (e.g. "Google Gemini", "Smart Local Heuristic AI").
     */
    String getProviderName();
}
