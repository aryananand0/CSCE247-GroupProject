package model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Singleton class that maintains a list of achievements and provides methods to manage them.
 * Ensures only one instance of the AchievementList exists.
 */
public class AchievementList {

    // Attributes
    private ArrayList<Achievements> achievements;
    private static AchievementList instance;

    /**
     * Private constructor to initialize the list of achievements from data loader.
     */
    private AchievementList() {
        achievements = DataLoader.loadAchievements();
    }

    /**
     * Retrieves the single instance of the AchievementList class.
     * Creates a new instance if one doesn't already exist.
     * 
     * @return The single instance of AchievementList.
     */
    public static AchievementList getInstance() {
        if (instance == null) {
            instance = new AchievementList();
        }
        return instance;
    }

    /**
     * Adds a new achievement to the list if it is not null and not already present.
     * 
     * @param ach The achievement to add.
     */
    public void addAchievement(Achievements ach) {
        if (ach != null && !achievements.contains(ach)) {
            achievements.add(ach);
        }
    }

    /**
     * Retrieves an achievement by its unique identifier (UUID).
     * 
     * @param achId The UUID of the achievement.
     * @return The achievement if found; otherwise, null.
     */
    public Achievements getAchievementById(UUID achId) {
        for (Achievements ach : achievements) {
            if (ach.getAchievementId().equals(achId)) {
                return ach;
            }
        }
        return null; // Return null if achievement not found
    }

    /**
     * Retrieves an achievement by its title, case-insensitive.
     * 
     * @param achievementTitle The title of the achievement.
     * @return The achievement if found; otherwise, null.
     */
    public Achievements getAchievementByTitle(String achievementTitle) {
        for (Achievements ach : achievements) {
            if (ach.getTitle().equalsIgnoreCase(achievementTitle)) {
                return ach;
            }
        }
        return null; // Return null if achievement not found
    }

    /**
     * Gets the total number of achievements in the list.
     * 
     * @return The size of the achievements list.
     */
    public int getTotalAchievements() {
        return achievements.size();
    }

    /**
     * Removes an achievement from the list by its title, case-insensitive.
     * 
     * @param achTitle The title of the achievement to remove.
     * @return true if the achievement was removed; false if not found.
     */
    public boolean removeAchievementByTitle(String achTitle) {
        for (Achievements ach : achievements) {
            if (ach.getTitle().equalsIgnoreCase(achTitle)) {
                achievements.remove(ach);
                return true;  // Achievement removed
            }
        }
        return false;  // Achievement not found
    }

    /**
     * Retrieves the complete list of achievements.
     * 
     * @return A list of all achievements.
     */
    public ArrayList<Achievements> getAchievements() {
        return this.achievements;
    }
}
