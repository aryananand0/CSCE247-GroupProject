package model;

import java.util.ArrayList;

public class Achievements {

    // Attributes
    private String title;
    private ArrayList<String> achievements;
    private double rewardPoints;
    private String description;

    // Constructor
    public Achievements(String title, String description, double rewardPoints) {
        this.title = title;
        this.achievements = new ArrayList<>();
        this.description = description;
        this.rewardPoints = rewardPoints;
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
