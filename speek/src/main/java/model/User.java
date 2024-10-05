package model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    // Attributes
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double progress;
    private double score;
    private boolean dailyReminder;
    private ArrayList<Language> favoriteLanguages;
    private ArrayList<Course> currentCourses;
    private ArrayList<Achievement> achievements;
    private HashMap<User, Double> progressPerUser;

    // Constructor with parameters
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.progress = 0.0;
        this.score = 0.0;
        this.dailyReminder = false;
        this.favoriteLanguages = new ArrayList<>();
        this.currentCourses = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.progressPerUser = new HashMap<>();
    }

    // Default constructor
    public User() {
        this("", "", "", "");
    }

    // Method to log in, needs implementation
    public boolean login(String email, String password) {
        // Check credentials and return true/false needs implementation
        return this.email.equals(email) && this.password.equals(password);
    }

    // Method to update profile needs implementation
    public void updateProfile(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Method to track progress needs implementation
    public double trackProgress() {
        // Return current progress needs implementation
        return this.progress;
    }

    // Method to get progress for a specific course and user needs implementation
    public double getProgress(Course course, User user) {
        // Retrieve progress for the given user and course needs implementation
        return progressPerUser.getOrDefault(user, 0.0);
    }

    // Method to get score needs implementation
    public double getScore() {
        return this.score;
    }

    // Method to increase score needs implementation
    public void increaseScore() {
        this.score += 1.0;
    }

    // Method to decrease score needs implementation
    public void decreaseScore() {
        if (this.score > 0) {
            this.score -= 1.0;
        }
    }

    // Method to update user progress needs implementation
    public void updateUserProgress(User user, Double completion) {
        this.progressPerUser.put(user, completion);
    }

   
}

