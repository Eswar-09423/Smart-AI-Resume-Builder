package com.smartresume.ai.repository;

import com.smartresume.ai.exception.FileStorageException;
import com.smartresume.ai.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 5. INTERFACE (Standard Contract)
 * 9. GENERICS (Generic type parameters <T, ID>)
 * 7. EXCEPTION HANDLING (Declaring throws FileStorageException)
 * 8. COLLECTIONS (Returns List<T>)
 * ============================================================================
 * @param <T>  The entity type
 * @param <ID> The primary key / identifier type
 */
public interface IRepository<T, ID> {

    /**
     * Persists an entity with the specified identifier
     */
    T save(ID id, T entity) throws FileStorageException;

    /**
     * Finds entity by ID, wrapped in Optional
     */
    Optional<T> findById(ID id) throws FileStorageException;

    /**
     * Returns all stored entities
     */
    List<T> findAll() throws FileStorageException;

    /**
     * Deletes entity by ID
     */
    boolean deleteById(ID id) throws FileStorageException, ResourceNotFoundException;

    /**
     * Checks if entity exists
     */
    boolean existsById(ID id);
}
