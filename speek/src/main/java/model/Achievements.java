package model;

public class Achievements {

    // Attributes
    private String title;
    private String description;
    private int rewardPoints;

    // Constructor
    public Achievements(String title, String description, int rewardPoints) {
        this.title = title;
        this.description = description;
        this.rewardPoints = rewardPoints;
    }

    // Getters and Setters
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

    // toString() method to display achievement details
    @Override
    public String toString() {
        return "Achievement Title: " + title + " | Description: " + description + " | Reward Points: " + rewardPoints;
    }
}
