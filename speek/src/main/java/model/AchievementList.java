package model;

import java.util.ArrayList;

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

    // Method to add a achievement to the list
    public void addAchievement(Achievements ach) {
        if (ach != null && !achievements.contains(ach)) {
            achievements.add(ach);
        }
    }





    
}
