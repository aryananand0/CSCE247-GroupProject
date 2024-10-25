package model;

import java.util.ArrayList;
import java.util.UUID;

public class LearningAppFacade {

    private ArrayList<Language> languages; 
    private UserList user;
    private CourseList courses;
    private LessonList lessons;
    private User currentUser;  // Store the current logged-in user
    private static LearningAppFacade instance;  // Singleton instance
    private AchievementList achievements;
    private LeaderboardList leaderboardList;

    // Constructor
    private LearningAppFacade() {
        user = UserList.getInstance();
        courses = CourseList.getInstance();
        lessons = LessonList.getInstance();
        // TODO: Change languages so not access DataLoader directly
        languages = DataLoader.loadLanguages();
        achievements = AchievementList.getInstance();
        leaderboardList = LeaderboardList.getInstance();
    }

    // Get the singleton instance of LearningAppFacade
    public static LearningAppFacade getInstance() {
        if (instance == null) {
            instance = new LearningAppFacade();
        }
        return instance;
    }
    // Methods

    public ArrayList<Question> getQuestions(UUID lesson) {
        return new ArrayList<>(lessons.getQuestions(lesson));
    }
    // Registers a new user with email and password, returns the created User object
    public boolean registerUser(String username, String firstName, String lastName,String email, String password) {
        return user.addUser(username, firstName, lastName, email, password);
    }

    // Loads the users
    public ArrayList<User> loadUsers(){
        return this.user.getUsers();
    }

    // Loads the Courses
    public ArrayList<Course> loadCourses(){
        return this.courses.getCourses();
    }

    // loads the lessons
    public ArrayList<Lesson> loadLesson(){
        return this.lessons.getLessons();
    }

    // Load languages 
    // TODO:
    public ArrayList<Language> loadLanguages() {
        return this.languages;
    }

    // Load achievments 
    // TODO:
    public ArrayList<Achievements> loadAchievements() {
        return this.achievements.getAchievements();
    }

    // Load courses TODO:
    
    // Load leaderboard
    public Leaderboard loadLeaderboard() {
        return this.leaderboardList.getLeaderboard();
    }

    // Logs in a user with username or email and password
    public User loginUser(String usernameOrEmail, String password) {
        // Check if login is valid
        if (user.loginCheck(usernameOrEmail, password)) {
            currentUser = user.getUser(usernameOrEmail, password);  // Retrieve the user
            return currentUser;  // Return the user on successful login
        }
        return null;  // Return null on login failure
    }

    // Method to get the currently logged-in user
    public User getCurrentUser() {
        return currentUser;
    }

    // Logs out the current user
    public boolean logout() {
        return user.logout();
    }

    // Enrolls a user in a course
    public void enrollUserInCourse(User user, Course course) {
        // Method stub
    }

    // Tracks the user's progress in a specific course
    public double trackUserProgress(User user, Course course) {
        // Method stub
        return 0.0;  // Placeholder return
    }

    // Allows the user to take a quiz
    public void takeQuiz(User user, Quiz quiz) {
        // Method stub
    }

    /**
    // Retrieves the leaderboard
    public Leaderboard getLeaderboard() {
        return DataLoader.loadLeaderboard();
    }
    */
    
}
