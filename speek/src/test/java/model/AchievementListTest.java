package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AchievementListTest {

    private AchievementList achievementList;
    private Achievements achievement1;
    private Achievements achievement2;
    
    @BeforeEach
    public void setUp() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("User.json");
        achievementList = AchievementList.getInstance();
        achievementList.getAchievements().clear(); // Clear achievements before each test
        
        // Creating achievements for testing
        achievement1 = new Achievements(UUID.randomUUID().toString(), "First Achievement", "Description of first achievement", 10);
        achievement2 = new Achievements(UUID.randomUUID().toString(), "Second Achievement", "Description of second achievement", 20);
    }

    @Test
    public void testAddAchievement() {
        achievementList.addAchievement(achievement1);
        assertEquals(1, achievementList.getTotalAchievements());
        assertTrue(achievementList.getAchievements().contains(achievement1));
    }

    @Test
    public void testAddDuplicateAchievement() {
        achievementList.addAchievement(achievement1);
        achievementList.addAchievement(achievement1); // Attempt to add the same achievement again
        assertEquals(1, achievementList.getTotalAchievements()); // Should still be 1
    }

    @Test
    public void testGetAchievementById() {
        achievementList.addAchievement(achievement1);
        Achievements retrievedAchievement = achievementList.getAchievementById(UUID.fromString(achievement1.getAchievementId()));
        assertNotNull(retrievedAchievement);
        assertEquals(achievement1.getTitle(), retrievedAchievement.getTitle());
    }

    @Test
    public void testGetAchievementByTitle() {
        achievementList.addAchievement(achievement1);
        Achievements retrievedAchievement = achievementList.getAchievementByTitle(achievement1.getTitle());
        assertNotNull(retrievedAchievement);
        assertEquals(achievement1.getTitle(), retrievedAchievement.getTitle());
    }

    @Test
    public void testRemoveAchievementByTitle() {
        achievementList.addAchievement(achievement1);
        assertTrue(achievementList.removeAchievementByTitle(achievement1.getTitle()));
        assertEquals(0, achievementList.getTotalAchievements());
    }

    @Test
    public void testRemoveNonExistentAchievement() {
        assertFalse(achievementList.removeAchievementByTitle("Non-existent Title"));
    }

    @Test
    public void testGetTotalAchievements() {
        assertEquals(0, achievementList.getTotalAchievements()); // No achievements initially
        achievementList.addAchievement(achievement1);
        assertEquals(1, achievementList.getTotalAchievements());
        achievementList.addAchievement(achievement2);
        assertEquals(2, achievementList.getTotalAchievements());
    }

    @Test
    public void testSingletonInstance() {
        AchievementList anotherInstance = AchievementList.getInstance();
        assertSame(achievementList, anotherInstance, "Both instances should be the same");
    }
}

