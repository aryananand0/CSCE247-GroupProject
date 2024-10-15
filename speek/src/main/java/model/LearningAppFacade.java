package model;

import java.util.ArrayList;

public class LearningAppFacade {

    private UserList user;
    private CourseList course;
    private User currentUser;  // Store the current logged-in user

    // Constructor
    public LearningAppFacade() {
        user = UserList.getInstance();
        course = CourseList.getInstance();
    }

    // Registers a new user with email and password
    public boolean registerUser(String username, String firstName, String lastName, String email, String password) {
        if (!user.haveUser(username)) {
            return user.addUser(username, firstName, lastName, email, password);
        }
        return false;
    }

    // Logs in a user with username or email and password
    public User loginUser(String usernameOrEmail, String password) {
        // Check if login is valid
        if (user.LoginCheck(usernameOrEmail, password)) {
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
    public static boolean logout() {
        LearningAppFacade facade = new LearningAppFacade();
        if (facade.getCurrentUser() != null) {
            facade.currentUser = null;
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
    public ArrayList<User> getLeaderboard() {
        // Method stub
        return new ArrayList<>();  // Placeholder return
    }
}
