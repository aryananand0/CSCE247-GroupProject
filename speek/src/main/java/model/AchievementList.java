package model;

import java.util.ArrayList;
import java.util.UUID;

public class AchievementList {

    // Attributes
    private ArrayList<Achievements> achievements;
    private static AchievementList instance;

    // Private Constructor
    private AchievementList() {
        achievements = DataLoader.loadAchievements();
    }

    // Singleton pattern to ensure a single instance of AchievementList
    public static AchievementList getInstance() {
        if(instance == null) {
            instance = new AchievementList();
        } 
        return instance;
    }    

    // Method to add an achievement to the list
    public void addAchievement(Achievements ach) {
        if (ach != null && !achievements.contains(ach)) {
            achievements.add(ach);
        }
    }

    // Method to retrieve a achievement by its UUID
    public Achievements getAchievementById(UUID achId) {
        for (Achievements ach : achievements) {
            if (ach.getAchievementId().equals(achId)) {
                return ach;
            }
        }
        return null; // Return null if achievement not found
    }

    // Method to retrieve an achievement by its title
    public Achievements getAchievementByTitle(String achievementTitle) {
        for (Achievements ach : achievements) {
            if (ach.getTitle().equalsIgnoreCase(achievementTitle)) {
                return ach;
            }
        }
        return null; // Return null if achievement not found
    }

    // Method to get the total number of achievement
    public int getTotalAchievements() {
        return achievements.size();
    }

    // Method to remove a achievements by title
    public boolean removeAchievementByTitle(String achTitle) {
        for (Achievements ach : achievements) {
            if (ach.getTitle().equalsIgnoreCase(achTitle)) {
                achievements.remove(ach);
                return true;  // Achievement removed
            }
        }
        return false;  // Achievement not found
    }

    // Get the list of all achievements
    public ArrayList<Achievements> getAchievements() {
        return this.achievements;
    }
}






    
}
