package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;

public class LearningAppFacadeTest {

    private LearningAppFacade facade;
    private UUID sampleUserId;
    private UUID sampleCourseId;

    @TempDir
    Path tempDir;

    // Paths to temporary JSON files
    private Path tempUserFile;
    private Path tempCourseFile;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize the facade and required fields
        facade = LearningAppFacade.getInstance();
        sampleUserId = UUID.randomUUID();
        sampleCourseId = UUID.randomUUID();

        // Set up temporary JSON paths
        tempUserFile = tempDir.resolve("json/Users.json");
        tempCourseFile = tempDir.resolve("jsonCourses.json");

        // Initialize directories
        Files.createDirectories(tempUserFile.getParent());
    }

    @Test
    public void testRegisterUser() {
        
        boolean result = facade.registerUser("newUser", "New", "User", "newuser@example.com", "newpassword");
        assertTrue(result, "User should be registered successfully");
        User registeredUser = UserList.getInstance().getUser("newUser");
        assertNotNull(registeredUser, "Registered user should not be null");
        assertEquals("New", registeredUser.getFirstName(), "First name should match");
        assertEquals("newuser@example.com", registeredUser.getEmail(), "Email should match");

    }

    @Test
    public void testRegisterCourse() {
        // Act: Register a course
        testRegisterUser();
        facade.registerCourse(sampleUserId, sampleCourseId);

        // Assert: Verify that the user is correctly registered for the course
        User user = UserList.getInstance().getUserById(sampleUserId);
        assertEquals(sampleCourseId.toString(), user.getCurrentCourseId(), "User's current course ID should match the registered course");
        assertTrue(user.getCurrentCourses().contains(CourseList.getInstance().getCourse(sampleCourseId)), "User should be enrolled in the registered course");
    }

    @Test
    public void testGetQuestions() {
        UUID lessonId = UUID.randomUUID();
        Lesson lesson = new Lesson(lessonId, "Sample Lesson");

        // Add a question to the lesson
        Question question = new MultipleChoiceQuestion("Sample question?", Arrays.asList("Option 1", "Option 2", "Option 3"), "Option 1");
        lesson.addQuestion(question);

        ArrayList<Question> questionList = new ArrayList<>(lesson.getQuestions());

        // Assert: Verify the question details
        assertEquals(1, questionList.size(), "Lesson should have one question");
        assertEquals("Sample question?", questionList.get(0).getText(), "Question text should match");
    }

    @Test
    public void testLoadUsers() {
        // Act: Load users
        ArrayList<User> users = facade.loadUsers();
        
        // Assert: Verify users are loaded correctly
        assertNotNull(users, "User list should not be null");
        assertTrue(users.size() > 0, "User list should contain users");
    }

    @Test
    public void testLoginUser() {
        // Act: Attempt to log in
        User loggedInUser = facade.loginUser("testUser", "password123");
        
        // Assert: Verify login success
        assertNotNull(loggedInUser, "User should be logged in successfully");
        assertEquals("testUser", loggedInUser.getUserName(), "Logged-in user's username should match");
    }

    @Test
    public void testLogout() {
        // Act: Log in and then log out
        facade.loginUser("testUser", "password123");
        boolean result = facade.logout();

        // Assert: Verify logout success
        assertTrue(result, "Logout should be successful");
        assertNull(facade.getCurrentUser(), "Current user should be null after logout");
    }

    @Test
    public void testLoadCourses() {
        // Act: Load courses
        ArrayList<Course> courses = facade.loadCourses();
        
        // Assert: Verify courses are loaded correctly
        assertNotNull(courses, "Course list should not be null");
        assertTrue(courses.size() > 0, "Course list should contain courses");
    }

    @Test
    public void testLoadLesson() {
        // Act: Load lessons
        ArrayList<Lesson> lessons = facade.loadLesson();
        
        // Assert: Verify lessons are loaded correctly
        assertNotNull(lessons, "Lesson list should not be null");
        assertTrue(lessons.size() > 0, "Lesson list should contain lessons");
    }

    @Test
    public void testLoadLanguages() {
        // Act: Load languages
        ArrayList<Language> languages = facade.loadLanguages();
        
        // Assert: Verify languages are loaded correctly
        assertNotNull(languages, "Languages list should not be null");
    }

    @Test
    public void testLoadAchievements() {
        // Act: Load achievements
        ArrayList<Achievements> achievements = facade.loadAchievements();
        
        // Assert: Verify achievements are loaded correctly
        assertNotNull(achievements, "Achievements list should not be null");
    }

    @Test
    public void testLoadLeaderboard() {
        // Act: Load leaderboard
        Leaderboard leaderboard = facade.loadLeaderboard();
        
        // Assert: Verify leaderboard is loaded correctly
        assertNotNull(leaderboard, "Leaderboard should not be null");
    }
    
    

}   
