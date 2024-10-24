package model;

import java.util.ArrayList;
import java.util.UUID;

public class AchievementList {

    // Attributes
    private ArrayList<Achievements> achievements;
    private static AchievementList instance;

    private AchievementList() {
        achievements = DataLoader.loadAchievements();
    }

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
        return null; // Return null if lesson not found
    }

    // Method to retrieve an achievement by its title
    public Achievements getLessonByTitle(String lessonTitle) {
        for (Achievements ach : achievements) {
            if (ach.getTitle().equalsIgnoreCase(lessonTitle)) {
                return ach;
            }
        }
        return null; // Return null if lesson not found
    }







    
}
