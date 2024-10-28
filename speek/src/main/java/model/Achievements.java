package model;

/**
 * Represents an achievement that a user can earn.
 * Each achievement has a unique identifier, title, description, and reward points.
 */
public class Achievements {

    // Attributes
    private String achievementId;
    private String title;
    private String description;
    private int rewardPoints;

    /**
     * Constructs an achievement with an ID, title, description, and reward points.
     * 
     * @param achievementId The unique identifier of the achievement.
     * @param title         The title of the achievement.
     * @param description   The description of the achievement.
     * @param rewardPoints  The reward points associated with the achievement.
     */
    public Achievements(String achievementId, String title, String description, int rewardPoints) {
        this.achievementId = achievementId;
        this.title = title;
        this.description = description;
        this.rewardPoints = rewardPoints;
    }

    /**
     * Constructs an achievement without an ID. The ID will be empty by default.
     * 
     * @param title         The title of the achievement.
     * @param description   The description of the achievement.
     * @param rewardPoints  The reward points associated with the achievement.
     */
    public Achievements(String title, String description, int rewardPoints) {
        this.title = title;
        this.description = description;
        this.rewardPoints = rewardPoints;
        this.achievementId = "";  // Default empty ID; can be generated later if needed
    }

    /**
     * Gets the unique identifier of the achievement.
     * 
     * @return The achievement ID.
     */
    public String getAchievementId() {
        return achievementId;
    }

    /**
     * Sets the unique identifier for the achievement.
     * 
     * @param achievementId The achievement ID to set.
     */
    public void setAchievementId(String achievementId) {
        this.achievementId = achievementId;
    }

    /**
     * Gets the title of the achievement.
     * 
     * @return The title of the achievement.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the achievement.
     * 
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the achievement.
     * 
     * @return The description of the achievement.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the achievement.
     * 
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the reward points associated with the achievement.
     * 
     * @return The reward points of the achievement.
     */
    public int getRewardPoints() {
        return rewardPoints;
    }

    /**
     * Sets the reward points for the achievement.
     * 
     * @param rewardPoints The reward points to set.
     */
    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    /**
     * Returns a string representation of the achievement, including the title, description,
     * and reward points.
     * 
     * @return A string representation of the achievement.
     */
    @Override
    public String toString() {
        return "Achievement Title: " + title + ", Description: " + description + ", Reward Points: " + rewardPoints;
    }
}
