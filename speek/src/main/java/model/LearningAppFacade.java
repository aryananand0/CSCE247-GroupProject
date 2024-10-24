package model;

import java.util.ArrayList;

public class LearningAppFacade {

    private ArrayList<Language> languages; 
    private UserList user;
    private CourseList course;
    private LessonList lessons;
    private User currentUser;  // Store the current logged-in user
    private static LearningAppFacade instance;  // Singleton instance
    private AchievementList achievements;
    private LeaderboardList leaderboardList;

    // Constructor
    private LearningAppFacade() {
        user = UserList.getInstance();
        course = CourseList.getInstance();
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

    // Registers a new user with email and password, returns the created User object
    public boolean registerUser(String username, String firstName, String lastName,String email, String password) {
        user = UserList.getInstance();
        if(user.haveUser(username)) {
            return false;
        } else {
            user.addUser(username, firstName, lastName, email, password);
            return true;
        }
    }

    // Loads the users
    public ArrayList<User> loadUsers(){
        return this.user.getUsers();
    }

    // Loads the Courses
    public ArrayList<Course> loadCourses(){
        return this.course.getCourses();
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
            currentUser = DataLoader.getUser(usernameOrEmail, password);  // Retrieve the user
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
        if (currentUser != null) {
            currentUser = null;
            return true;
        }
        return false;
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

    // Retrieves the leaderboard
    public Leaderboard getLeaderboard() {
        return DataLoader.loadLeaderboard();
    }
}
