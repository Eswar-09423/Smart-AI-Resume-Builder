package com.smartresume.ai.model;

import com.smartresume.ai.exception.ResumeValidationException;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 5. INTERFACE
 * ============================================================================
 * An Interface defines a standard contract for any entity that requires
 * validation before processing or persistence.
 * Any class that implements IValidatable MUST provide concrete validation logic.
 * ============================================================================
 */
public interface IValidatable {

    /**
     * Validates the internal state of the implementing object.
     * Throws ResumeValidationException if validation rules fail.
     *
     * @throws ResumeValidationException when required fields or format constraints are violated.
     */
    void validate() throws ResumeValidationException;
}
