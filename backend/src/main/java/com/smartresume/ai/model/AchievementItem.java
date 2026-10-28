package com.smartresume.ai.model;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 4. INHERITANCE
 * ============================================================================
 */
public class AchievementItem extends ResumeSectionItem {

    private String achievementText;

    public AchievementItem() {
        super();
    }

    public AchievementItem(String achievementText) {
        super("Achievement", achievementText);
        this.achievementText = achievementText;
    }

    @Override
    public String getDisplayTitle() {
        return "Achievement";
    }

    @Override
    public String toFormattedText() {
        return String.format("• %s", (achievementText != null ? achievementText : description));
    }

    public String getAchievementText() {
        return achievementText;
    }

    public void setAchievementText(String achievementText) {
        this.achievementText = achievementText;
        this.description = achievementText;
    }
}
