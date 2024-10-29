/**
package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class AchievementListTest {
    private AchievementList achievementList;
    private Achievements sampleAchievement;

    @BeforeEach
    void setUp() {
        achievementList = AchievementList.getInstance();
        sampleAchievement = new Achievements(UUID.randomUUID(), "Sample Achievement", "A sample description");
        achievementList.getAchievements().clear(); // Clear existing achievements to start fresh for each test
    }

    @Test
    void testSingletonInstance() {
        AchievementList instance1 = AchievementList.getInstance();
        AchievementList instance2 = AchievementList.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    void testAddAchievement() {
        achievementList.addAchievement(sampleAchievement);
        assertTrue(achievementList.getAchievements().contains(sampleAchievement), "Achievement should be added");
    }

    @Test
    void testGetAchievementById() {
        UUID id = sampleAchievement.getAchievementId();
        achievementList.addAchievement(sampleAchievement);
        Achievements foundAchievement = achievementList.getAchievementById(id); // Pass UUID
        assertEquals(sampleAchievement, foundAchievement, "Should return the achievement with the specified ID");
    }

    @Test
    void testGetAchievementByTitle() {
        achievementList.addAchievement(sampleAchievement);
        Achievements foundAchievement = achievementList.getAchievementByTitle("Sample Achievement"); // Pass String
        assertEquals(sampleAchievement, foundAchievement, "Should return the achievement with the specified title");
    }

    @Test
    void testGetTotalAchievements() {
        achievementList.addAchievement(sampleAchievement);
        assertEquals(1, achievementList.getTotalAchievements(), "Should return the correct number of achievements");
    }

    @Test
    void testRemoveAchievementByTitle() {
       achievementList.addAchievement(sampleAchievement);
        boolean removed = achievementList.removeAchievementByTitle("Sample Achievement");
        assertTrue(removed, "Achievement should be removed");
        assertFalse(achievementList.getAchievements().contains(sampleAchievement), "Achievement should no longer be in the list");
    }
}
*/

