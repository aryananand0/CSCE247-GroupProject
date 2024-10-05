package model;

import java.util.ArrayList;

public class Achievements {

    // Attributes
    private String title;
    private String description;
    private int rewardPoints;
    private ArrayList<String> achievements;

    // Constructor
    public Achievements(String title, String description, int rewardPoints) {
        this.title = title;
        this.description = description;
        this.rewardPoints = rewardPoints;
        this.achievements = new ArrayList<>();
    }

    // Method to notify achievement 
    public String notifyAchievement() {
        // Return a notification message 
        return "Achievement unlocked: " + title;
    }

    // Method to log achievements 
    public void achievementLog(ArrayList<String> achievements) {
        // Log achievements, needs to be implemented
        this.achievements.addAll(achievements);
    }

    
}
