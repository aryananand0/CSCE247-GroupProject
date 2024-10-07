package model;

import java.util.ArrayList;

public class Achievements {

    // Attributes
    private final String title;
    private final ArrayList<String> achievements;

    // Constructor
    public Achievements(String title, String description) {
        this.title = title;
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
