package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class LearningAppFacadeTest {

    private LearningAppFacade facade;
    private UUID sampleUserId;
    private UUID sampleCourseId;
    private Lesson lesson;

    @BeforeEach
    public void setUp() {
        // Initialize the facade and required fields
        facade = LearningAppFacade.getInstance();
        sampleUserId = UUID.randomUUID();
        sampleCourseId = UUID.randomUUID();

        // Set up sample data
        User sampleUser = new User(sampleUserId, "testUser", "Test", "User");
        Course sampleCourse = new Course(sampleCourseId, "Test Course");
        
        UserList.getInstance().clearUserList();  // Clear list for test consistency
        UserList.getInstance().addUser("testUser", "Test", "User", "test@example.com", "password123");
        CourseList.getInstance().addCourse(sampleCourse);
    }

    @Test
    public void testRegisterUser() {
        boolean result = facade.registerUser("newUser", "New", "User", "newuser@example.com", "newpassword");
        assertTrue(result, "User should be registered successfully");
        
        // Verify that the user has been added to the user list
        User registeredUser = UserList.getInstance().getUser("newUser");
        assertNotNull(registeredUser, "Registered user should not be null");
        assertEquals("New", registeredUser.getFirstName(), "First name should match");
        assertEquals("newuser@example.com", registeredUser.getEmail(), "Email should match");
    }

    @Test
    public void testRegisterCourse() {
        facade.registerCourse(sampleUserId, sampleCourseId);
        
        User user = UserList.getInstance().getUserById(sampleUserId);
        assertEquals(sampleCourseId.toString(), user.getCurrentCourseId(), "User's current course ID should match the registered course");
        assertTrue(user.getCurrentCourses().contains(CourseList.getInstance().getCourse(sampleCourseId)), "User should be enrolled in the registered course");
    }

    @Test
    public void testGetQuestions() {
        
        UUID lessonId = UUID.randomUUID();
        Lesson lesson = new Lesson(lessonId, "Sample Lesson");

        // Create a concrete question instance (replace with your concrete class)
        Question question = new MultipleChoiceQuestion("Sample question?", Arrays.asList("Option 1", "Option 2", "Option 3"), "Option 1");
        lesson.addQuestion(question);  // Add the question to the lesson

        // Retrieve questions from the lesson
        ArrayList<Question> questionList = new ArrayList<>(lesson.getQuestions());

        // Assertions
        assertEquals(1, questionList.size(), "Lesson should have one question");
        assertEquals("Sample question?", questionList.get(0).getText(), "Question text should match");
    }

    @Test
    public void testLoadUsers() {
        ArrayList<User> users = facade.loadUsers();
        assertNotNull(users, "User list should not be null");
        assertTrue(users.size() > 0, "User list should contain users");
    }

    @Test
    public void testLoginUser() {
        User loggedInUser = facade.loginUser("testUser", "password123");
        assertNotNull(loggedInUser, "User should be logged in successfully");
        assertEquals("testUser", loggedInUser.getUserName(), "Logged-in user's username should match");
    }

    @Test
    public void testLogout() {
        facade.loginUser("testUser", "password123");
        boolean result = facade.logout();
        
        assertTrue(result, "Logout should be successful");
        assertNull(facade.getCurrentUser(), "Current user should be null after logout");
    }

    @Test
    public void testLoadCourses() {
        ArrayList<Course> courses = facade.loadCourses();
        assertNotNull(courses, "Course list should not be null");
        assertTrue(courses.size() > 0, "Course list should contain courses");
    }

    @Test
    public void testLoadLesson() {
        ArrayList<Lesson> lessons = facade.loadLesson();
        assertNotNull(lessons, "Lesson list should not be null");
        assertTrue(lessons.size() > 0, "Lesson list should contain lessons");
    }

    @Test
    public void testLoadLanguages() {
        ArrayList<Language> languages = facade.loadLanguages();
        assertNotNull(languages, "Languages list should not be null");
    }

    @Test
    public void testLoadAchievements() {
        ArrayList<Achievements> achievements = facade.loadAchievements();
        assertNotNull(achievements, "Achievements list should not be null");
    }

    @Test
    public void testLoadLeaderboard() {
        Leaderboard leaderboard = facade.loadLeaderboard();
        assertNotNull(leaderboard, "Leaderboard should not be null");
    }
}
