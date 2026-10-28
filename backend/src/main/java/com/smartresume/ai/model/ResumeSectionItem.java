package com.smartresume.ai.model;

import java.util.UUID;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 4. INHERITANCE (ABSTRACT BASE CLASS)
 * ============================================================================
 * Abstract superclass serving as the foundation for resume sections.
 * Demonstrates:
 * - Common state (id, title, description, timestamp, orderIndex)
 * - Encapsulation (protected fields, public getters/setters)
 * - Constructor chaining (super() calls in child classes)
 * - Polymorphism via abstract methods: getDisplayTitle() and toFormattedText()
 * ============================================================================
 */
public abstract class ResumeSectionItem {

    // Protected fields accessible by subclasses (Inheritance)
    protected String id;
    protected String title;
    protected String description;

    // Primitive Data Types (Concept 1: Data Types)
    protected long createdAtTimestamp;
    protected int orderIndex;

    // Default constructor
    public ResumeSectionItem() {
        this.id = UUID.randomUUID().toString();
        this.createdAtTimestamp = System.currentTimeMillis();
        this.orderIndex = 0;
    }

    // Parameterized constructor
    public ResumeSectionItem(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }

    // Abstract methods enforcing polymorphic behavior in child classes
    public abstract String getDisplayTitle();
    public abstract String toFormattedText();

    // Getters and Setters (Encapsulation)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    public void setCreatedAtTimestamp(long createdAtTimestamp) {
        this.createdAtTimestamp = createdAtTimestamp;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}
