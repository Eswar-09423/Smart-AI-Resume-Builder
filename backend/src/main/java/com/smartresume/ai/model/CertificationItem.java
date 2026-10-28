package com.smartresume.ai.model;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 4. INHERITANCE
 * ============================================================================
 */
public class CertificationItem extends ResumeSectionItem {

    private String name;
    private String organization;
    private String year;

    public CertificationItem() {
        super();
    }

    public CertificationItem(String name, String organization, String year) {
        super(name, organization);
        this.name = name;
        this.organization = organization;
        this.year = year;
    }

    @Override
    public String getDisplayTitle() {
        return name;
    }

    @Override
    public String toFormattedText() {
        return String.format("• %s - %s (%s)", name, organization, year);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.title = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
        this.description = organization;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
