package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class User {

    // Attributes
    private UUID userId;  // Unique identifier for the user
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double progress;
    private double score;
    private boolean dailyReminder;
    private ArrayList<Language> favoriteLanguages;
    private ArrayList<Course> currentCourses;
    private ArrayList<Achievements> achievements;
    private HashMap<Course, Double> progressPerUser;

    // Constructor with parameters
    public User(String userName, String firstName, String lastName, String email, String password) {
        this.userId = UUID.randomUUID();  // Automatically generate a UUID
        this.userName =  userName;
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

    
    // This is for existing users in DataLoader
public User(UUID uuid, String userName, String firstName, String lastName, String email) {
    this.userId = uuid;  
    this.userName = userName != null ? userName : "";  // Initialize userName properly
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = "";
    this.favoriteLanguages = new ArrayList<>();  // Initialize
    this.currentCourses = new ArrayList<>();  // Initialize
    this.achievements = new ArrayList<>();  // Initialize
    this.progressPerUser = new HashMap<>();  // Initialize
}


    // Default Constructor
    public User() {
        this.userId = UUID.randomUUID();  // Automatically generate a UUID
        this.userName = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.progress = 0.0;
        this.score = 0.0;
        this.dailyReminder = false;
        this.favoriteLanguages = new ArrayList<>();
        this.currentCourses = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.progressPerUser = new HashMap<>();
    }

    // Constructor for leaderboard purposes 
    public User(UUID uuid,String userName, String firstName, String lastName, double score) {
        this.userId = uuid;  
        this.userName =  userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
    }

    public User(String username,String firstName, String lastName, String email) {
        this.userId = UUID.randomUUID();
        this.firstName = firstName;
        this.userName = username;
        this.lastName = lastName;
        this.email = email;
        this.password = "";  // Default password or can leave empty
        this.progress = 0.0;
        this.score = 0.0;
        this.dailyReminder = false;
        this.favoriteLanguages = new ArrayList<>();
        this.currentCourses = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.progressPerUser = new HashMap<>();
    }

    // Method to login
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    // Method to update profile information
    public void updateProfile(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Method to track progress for all courses
    public double trackProgress() {
        double totalProgress = 0.0;
        for (Course course : currentCourses) {
            totalProgress += progressPerUser.getOrDefault(course, 0.0);
        }
        return totalProgress / currentCourses.size();
    }

    // Method to get progress for a specific course
    public double getProgress(Course course) {
        return progressPerUser.getOrDefault(course, 0.0);
    }

    // Method to get user's score
    public double getScore() {
        return score;
    }

    // Method to set user's score
    public void setScore(double score) {
        this.score = score;
    }

    // Method to increase score
    public void increaseScore(double increment) {
        this.score += increment;
    }

    // Method to decrease score
    public void decreaseScore(double decrement) {
        this.score -= decrement;
    }

    // Method to update progress for a specific course
    public void updateUserProgress(Course course, double completion) {
        progressPerUser.put(course, completion);
    }

    // Getters and Setters for userId
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    // Getters and Setters for other attributes
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public boolean isDailyReminder() {
        return dailyReminder;
    }

    public void setDailyReminder(boolean dailyReminder) {
        this.dailyReminder = dailyReminder;
    }

    public ArrayList<Language> getFavoriteLanguages() {
        return favoriteLanguages;
    }

    public void setFavoriteLanguages(ArrayList<Language> favoriteLanguages) {
        this.favoriteLanguages = favoriteLanguages;
    }

    public ArrayList<Course> getCurrentCourses() {
        return currentCourses;
    }

    public void setCurrentCourses(ArrayList<Course> currentCourses) {
        this.currentCourses = currentCourses;
    }

    public ArrayList<Achievements> getAchievements() {
        return achievements;
    }

    public void setAchievements(ArrayList<Achievements> achievements) {
        this.achievements = achievements;
    }

    public HashMap<Course, Double> getProgressPerUser() {
        return progressPerUser;
    }

    public void setProgressPerUser(HashMap<Course, Double> progressPerUser) {
        this.progressPerUser = progressPerUser;
    }
    public String getUserName(){
        return this.userName;
    }

    // Leaderboard printing method
    public String PrintLeaderboard() {
        return "USERNAME: "+this.getUserName()+" | NAME: " + this.getFirstName() + " " + this.getLastName() + " | SCORE: " + this.getScore();
    }

    // Overriding toString() method to display user details
    @Override
    public String toString() {
        return "USERNAME: "+this.getUserName()+" | NAME: "  + this.getFirstName() + " " + this.getLastName() + " | EMAIL: " + this.getEmail();
    }
}
