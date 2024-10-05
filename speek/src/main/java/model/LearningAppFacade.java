package model;

import java.util.ArrayList;

public class LearningAppFacade {
    // Attributes
    private ArrayList<User> users;
    private ArrayList<Course> courses;
    private User currentUser;

    // Constructor
    public LearningAppFacade() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        currentUser = null;
    }

    // Methods

    // Registers a new user with email and password, returns the created User object
    public User registerUser(String email, String password) {
        // Method stub
        return null;  // Placeholder return
    }

    // Logs in a user with email and password, returns the User object if successful
    public User loginUser(String email, String password) {
        // Method stub
        return null;  // Placeholder return
    }

    // Enrolls a user in a course
    public void enrollUserInCourse(User user, Course course) {
        // Method stub
    }

    // Tracks the user's progress in a specific course, returns progress as a double
    public double trackUserProgress(User user, Course course) {
        // Method stub
        return 0.0;  // Placeholder return
    }

    // Allows the user to take a quiz
    public void takeQuiz(User user, Quiz quiz) {
        // Method stub
    }

    // Retrieves the leaderboard as an ArrayList of users
    public ArrayList<User> getLeaderboard() {
        // Method stub
        return new ArrayList<>();  // Placeholder return
    }

    // Logs out the current user
    public void logout() {
        // Method stub
    }
}
