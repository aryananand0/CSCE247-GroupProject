package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.UUID;

public class DataWriterTest {

    @BeforeAll
    public static void setupClass() {
        // Set file paths for testing to use test JSON files
        DataWriter.setFilePathsForTesting();
    }

    @AfterAll
    public static void teardownClass() {
        // Reset file paths to original JSON files after all tests
        DataWriter.resetFilePaths();
    }

    @BeforeEach
    public void setup() {
        // Clear out test files by saving empty lists initially
        DataWriter.saveUser1(new ArrayList<>());
        DataWriter.saveCourses(new ArrayList<>());
        DataWriter.saveAchievements(new ArrayList<>());
    }

    @Test
    public void testSaveUser() {
        ArrayList<User> users = new ArrayList<>();
        User user = new User(UUID.randomUUID(), "testUser", "Test", "User");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        users.add(user);

        // Save and load users
        DataWriter.saveUser1(users);
        ArrayList<User> loadedUsers = DataLoader.loadUsers();

        assertEquals(1, loadedUsers.size());
        assertEquals("testUser", loadedUsers.get(0).getUserName());
    }

    @Test
    public void testSaveCourse() {
        ArrayList<Course> courses = new ArrayList<>();
        Course course = new Course(UUID.randomUUID(), "Test Course", "Beginner", 0.0);
        courses.add(course);

        // Save and load courses
        DataWriter.saveCourses(courses);
        ArrayList<Course> loadedCourses = DataLoader.loadCourses();

        assertEquals(1, loadedCourses.size());
        assertEquals("Test Course", loadedCourses.get(0).getCourseName());
    }

    @Test
    public void testSaveAchievement() {
        ArrayList<Achievements> achievements = new ArrayList<>();
        Achievements achievement = new Achievements("Test Achievement", "Achievement Description", 100);
        achievements.add(achievement);

        // Save and load achievements
        DataWriter.saveAchievements(achievements);
        ArrayList<Achievements> loadedAchievements = DataLoader.loadAchievements();

        assertEquals(1, loadedAchievements.size());
        assertEquals("Test Achievement", loadedAchievements.get(0).getTitle());
    }

    @Test
    public void testSaveLeaderboard() {
        Leaderboard leaderboard = new Leaderboard();
        User user = new User(UUID.randomUUID(), "testUser", "Test", "User", 500.0);
        leaderboard.addUser(user);

        // Save and load leaderboard
        DataWriter.saveLeaderboard(leaderboard);
        Leaderboard loadedLeaderboard = DataLoader.loadLeaderboard();

        assertEquals(1, loadedLeaderboard.getUser().size());
        assertEquals("Test User", loadedLeaderboard.getUser().get(0).getFirstName() + " " + loadedLeaderboard.getUser().get(0).getLastName());
    }
}
