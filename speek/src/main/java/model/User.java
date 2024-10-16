package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class User {

    // Attributes
    private final String userId;                              
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;                                
    private double score;
    private boolean dailyReminder;
    private List<Language> favoriteLanguages;
    private ArrayList<Course> currentCourses;
    private List<Achievements> achievements;
    private HashMap<String, Double> progressPerCourse;     

    // New Fields for Enhanced Tracking
    private String currentCourseId;                        
    private String currentLessonId;                       
    private List<String> completedCourseIds;               
    private List<String> completedLessonIds;               
    private List<QuestionHistory> questionHistory;          
    private Question currentQuestion;                      

    // Constructor with parameters
    public User(String userName, String firstName, String lastName, String email, String password) {
        this.userId = UUID.randomUUID().toString();                    
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;                           
        this.score = 0.0;
        this.dailyReminder = false;
        this.favoriteLanguages = new ArrayList<>();
        this.currentCourses = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.progressPerCourse = new HashMap<>();
        this.currentCourseId = "";                          
        this.currentLessonId = "";                          
        this.completedCourseIds = new ArrayList<>();
        this.completedLessonIds = new ArrayList<>();
        this.questionHistory = new ArrayList<>();
        this.currentQuestion = null;
    }

    // Constructor for existing users in DataLoader
    public User(String uuid, String userName, String firstName, String lastName) {
        this.userId = uuid != null ? uuid : UUID.randomUUID().toString();
        this.userName = userName != null ? userName : "";
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = "";
        this.password = "";                                  
        this.score = 0.0;
        this.dailyReminder = false;
        this.favoriteLanguages = new ArrayList<>();
        this.currentCourses = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.progressPerCourse = new HashMap<>();
        this.currentCourseId = "";                          
        this.currentLessonId = "";                          
        this.completedCourseIds = new ArrayList<>();
        this.completedLessonIds = new ArrayList<>();
        this.questionHistory = new ArrayList<>();
        this.currentQuestion = null;
    }

    // Default Constructor
    public User() {
        this.userId = UUID.randomUUID().toString();                    
        this.userName = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.score = 0.0;
        this.dailyReminder = false;
        this.favoriteLanguages = new ArrayList<>();
        this.currentCourses = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.progressPerCourse = new HashMap<>();
        this.currentCourseId = "";                          
        this.currentLessonId = "";                          
        this.completedCourseIds = new ArrayList<>();
        this.completedLessonIds = new ArrayList<>();
        this.questionHistory = new ArrayList<>();
        this.currentQuestion = null;
    }

    // Constructor for leaderboard purposes
    public User(String uuid, String userName, String firstName, String lastName, double score) {
        this.userId = uuid != null ? uuid : UUID.randomUUID().toString();
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = "";
        this.password = "";
        this.score = score;
        this.dailyReminder = false;
        this.favoriteLanguages = new ArrayList<>();
        this.currentCourses = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.progressPerCourse = new HashMap<>();
        this.currentCourseId = "";                          
        this.currentLessonId = "";                          
        this.completedCourseIds = new ArrayList<>();
        this.completedLessonIds = new ArrayList<>();
        this.questionHistory = new ArrayList<>();
        this.currentQuestion = null;
    }

    // Getter for userId
    public String getUserId() {
        return userId;
    }

    // Getter and Setter for userName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isDailyReminder() {
        return dailyReminder;
    }

    public void setDailyReminder(boolean dailyReminder) {
        this.dailyReminder = dailyReminder;
    }

    public List<Language> getFavoriteLanguages() {
        return new ArrayList<>(favoriteLanguages);
    }

    public void setFavoriteLanguages(List<Language> favoriteLanguages) {
        this.favoriteLanguages = favoriteLanguages != null ? new ArrayList<>(favoriteLanguages) : new ArrayList<>();
    }

    public ArrayList<Course> getCurrentCourses() {
        return new ArrayList<>(currentCourses);
    }

    public void setCurrentCourses(List<Course> currentCourses) {
        this.currentCourses = currentCourses != null ? new ArrayList<>(currentCourses) : new ArrayList<>();
    }

    
    public List<Achievements> getAchievements() {
        return new ArrayList<>(achievements);
    }

    public void setAchievements(List<Achievements> achievements) {
        this.achievements = achievements != null ? new ArrayList<>(achievements) : new ArrayList<>();
    }

    public HashMap<String, Double> getProgressPerCourse() {
        return new HashMap<>(progressPerCourse);
    }

    public void setProgressPerCourse(HashMap<String, Double> progressPerCourse) {
        this.progressPerCourse = progressPerCourse != null ? new HashMap<>(progressPerCourse) : new HashMap<>();
    }

    // **New Getters and Setters for Enhanced Fields**

    public String getCurrentCourseId() {
        return currentCourseId;
    }

    public void setCurrentCourseId(String currentCourseId) {
        this.currentCourseId = currentCourseId;
    }

    public String getCurrentLessonId() {
        return currentLessonId;
    }

    public void setCurrentLessonId(String currentLessonId) {
        this.currentLessonId = currentLessonId;
    }

    public List<String> getCompletedCourseIds() {
        return new ArrayList<>(completedCourseIds);
    }

    public void setCompletedCourseIds(List<String> completedCourseIds) {
        this.completedCourseIds = completedCourseIds != null ? new ArrayList<>(completedCourseIds) : new ArrayList<>();
    }

    public List<String> getCompletedLessonIds() {
        return new ArrayList<>(completedLessonIds);
    }

    public void setCompletedLessonIds(List<String> completedLessonIds) {
        this.completedLessonIds = completedLessonIds != null ? new ArrayList<>(completedLessonIds) : new ArrayList<>();
    }

    public List<QuestionHistory> getQuestionHistory() {
        return new ArrayList<>(questionHistory);
    }

    public void setQuestionHistory(List<QuestionHistory> questionHistory) {
        this.questionHistory = questionHistory != null ? new ArrayList<>(questionHistory) : new ArrayList<>();
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    
    public boolean login(String email, String password) {
        
        return this.email.equals(email) && this.password.equals(password); 
    }

    
    public void updateProfile(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    
    public void markLessonAsCompleted(String lessonId) {
        if (!completedLessonIds.contains(lessonId)) {
            completedLessonIds.add(lessonId);
            System.out.println("✅ Lesson ID " + lessonId + " marked as completed.");
        }
    }

    
    public void markCourseAsCompleted(String courseId) {
        if (!completedCourseIds.contains(courseId)) {
            completedCourseIds.add(courseId);
            // Remove from currentCourses if present
            currentCourses.removeIf(course -> course.getCourseId().equals(courseId));
            // Remove from progressPerCourse as it's completed
            progressPerCourse.remove(courseId);
            System.out.println("🏆 Course ID " + courseId + " marked as completed.");
        }
    }

    


    
    public void removeCourse(String courseId) {
        boolean removed = currentCourses.removeIf(course -> course.getCourseId().equals(courseId));
        if (removed) {
            progressPerCourse.remove(courseId);
            System.out.println("❌ Course ID " + courseId + " removed from current courses.");
        } else {
            System.out.println("⚠️ Course ID " + courseId + " not found in current courses.");
        }
    }

    
    public double trackProgress() {
        if (progressPerCourse.isEmpty()) {
            return 0.0;
        }
        double totalProgress = 0.0;
        for (double courseProgress : progressPerCourse.values()) {
            totalProgress += courseProgress;
        }
        return totalProgress / progressPerCourse.size();
    }

    
    public double getProgress(String courseId) {
        return progressPerCourse.getOrDefault(courseId, 0.0);
    }

   
    public void updateUserProgress(String courseId, double completion) {
        if (progressPerCourse.containsKey(courseId)) {
            progressPerCourse.put(courseId, completion);
            System.out.println(" Updated progress for Course ID " + courseId + " to " + String.format("%.2f", completion) + "%.");
        } else {
            System.out.println("⚠️ Course ID " + courseId + " not found in progress tracking.");
        }
    }

    
    public void displayProgress() {
        System.out.println("\n=== User Progress ===");
        System.out.println("Overall Progress: " + String.format("%.2f", trackProgress()) + "%");
        System.out.println("Completed Courses: " + (completedCourseIds.isEmpty() ? "None" : completedCourseIds));
        System.out.println("Completed Lessons: " + (completedLessonIds.isEmpty() ? "None" : completedLessonIds));
        System.out.println("=====================");
    }

    
    public void sendDailyReminder() {
        if (dailyReminder) {
            System.out.println("⏰ Reminder: Continue your lessons to keep progressing!");
        }
    }

    
    public void unlockAchievement(String achievementId, String title, String description, int rewardPoints) {
        Achievements achievement = new Achievements(achievementId, title, description, rewardPoints);
        if (!achievements.contains(achievement)) {
            achievements.add(achievement);
            System.out.println("🏅 Achievement Unlocked: " + title);
            this.increaseScore(rewardPoints);
        }
    }

    
    public void increaseScore(double increment) {
        this.score += increment;
        System.out.println("Score increased by " + increment + ". New score: " + this.score);
    }

    
    public void decreaseScore(double decrement) {
        this.score -= decrement;
        System.out.println(" Score decreased by " + decrement + ". New score: " + this.score);
    }

    public String PrintLeaderboard() {
        return "USERNAME: "+this.getUserName()+" | NAME: " + this.getFirstName() + " " + this.getLastName() + " | SCORE: " + this.getScore();
    }

    // Overriding toString() method to display user details
    @Override
    public String toString() {
        return "USERNAME: " + this.getUserName() +
                " | NAME: " + this.getFirstName() + " " + this.getLastName() +
                " | EMAIL: " + this.getEmail();
    }
}
