package model;

public class Achievements {

    // Attributes
    private String achievementId;
    private String title;
    private String description;
    private int rewardPoints;

    // Four-parameter constructor (with achievementId)
    public Achievements(String achievementId, String title, String description, int rewardPoints) {
        this.achievementId = achievementId;
        this.title = title;
        this.description = description;
        this.rewardPoints = rewardPoints;
    }

    // Three-parameter constructor (without achievementId)
    public Achievements(String title, String description, int rewardPoints) {
        this.title = title;
        this.description = description;
        this.rewardPoints = rewardPoints;
        this.achievementId = "";  // Default empty ID or generate an ID if needed
    }

    // Getters and Setters
    public String getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(String achievementId) {
        this.achievementId = achievementId;
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

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    @Override
    public String toString() {
        return "Achievement Title: " + title + ", Description: " + description + ", Reward Points: " + rewardPoints;
    }
}
