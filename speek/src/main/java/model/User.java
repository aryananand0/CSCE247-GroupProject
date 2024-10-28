package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Represents a user within the system, encapsulating personal information,
 * progress tracking, course management, achievements, and interaction history.
 */
public class User {

    // Attributes
    private UUID userId;
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
    private List<Word> missedWords;

    // New Fields for Enhanced Tracking
    private String currentCourseId;
    private String currentLessonId;
    private List<String> completedCourseIds;
    private List<String> completedLessonIds;
    private List<QuestionHistory> questionHistory;
    private Question currentQuestion;

    /**
     * Constructs a new User with the specified details.
     *
     * @param userName  The username of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user.
     * @param password  The password for the user's account.
     */
    public User(String userName, String firstName, String lastName, String email, String password) {
        this.userId = UUID.randomUUID();
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
        this.missedWords = new ArrayList<>();
    }

    /**
     * Constructs a User with the specified UUID and basic details.
     * Intended for existing users loaded from a data source.
     *
     * @param uuid      The unique identifier of the user.
     * @param userName  The username of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     */
    public User(UUID uuid, String userName, String firstName, String lastName) {
        this.userId = uuid != null ? uuid : UUID.randomUUID();
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
        this.missedWords = new ArrayList<>();
    }

    /**
     * Constructs a default User with no initial data.
     */
    public User() {
        this.userId = UUID.randomUUID();
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
        this.missedWords = new ArrayList<>();
    }

    /**
     * Constructs a User for leaderboard purposes with a specified score.
     *
     * @param uuid       The unique identifier of the user.
     * @param userName   The username of the user.
     * @param firstName  The first name of the user.
     * @param lastName   The last name of the user.
     * @param score      The initial score of the user.
     */
    public User(UUID uuid, String userName, String firstName, String lastName, double score) {
        this.userId = uuid != null ? uuid : UUID.randomUUID();
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
        this.missedWords = new ArrayList<>();
    }

    /**
     * Adds a word to the list of missed words based on the word's ID.
     *
     * @param wordId The ID of the word to add.
     */
    public void addMissedWord(String wordName) {
        WordList wl = WordList.getInstance();
        Word word = wl.getWordByName(wordName);
        if (word != null) {
            missedWords.add(word);
        } else {
            System.out.println("Word does not exist in JSON.");
        }
    }

    /**
     * Retrieves the list of missed words.
     *
     * @return A new list containing all missed words.
     */
    public List<Word> getMissedWords() {
        return new ArrayList<>(missedWords);
    }

    /**
     * Clears all missed words from the user's history.
     */
    public void clearMissedWords() {
        missedWords.clear();
    }

    // Getters and Setters for all attributes, including new fields for enhanced tracking

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's UUID.
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param userId The UUID to set for the user.
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    /**
     * Gets the username of the user.
     *
     * @return The user's username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user.
     *
     * @param userName The username to set for the user.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName The first name to set for the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName The last name to set for the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email to set for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password to set for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's score.
     *
     * @return The user's score.
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the user's score.
     *
     * @param score The score to set for the user.
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Checks if the user has enabled daily reminders.
     *
     * @return True if daily reminders are enabled, otherwise false.
     */
    public boolean isDailyReminder() {
        return dailyReminder;
    }

    /**
     * Sets the user's preference for daily reminders.
     *
     * @param dailyReminder The preference to set.
     */
    public void setDailyReminder(boolean dailyReminder) {
        this.dailyReminder = dailyReminder;
    }

    /**
     * Gets the user's favorite languages.
     *
     * @return A new list containing the user's favorite languages.
     */
    public List<Language> getFavoriteLanguages() {
        return new ArrayList<>(favoriteLanguages);
    }

    /**
     * Sets the user's favorite languages.
     *
     * @param favoriteLanguages The list of favorite languages to set.
     */
    public void setFavoriteLanguages(List<Language> favoriteLanguages) {
        this.favoriteLanguages = favoriteLanguages != null ? new ArrayList<>(favoriteLanguages) : new ArrayList<>();
    }

    /**
     * Gets the list of courses the user is currently enrolled in.
     *
     * @return A new list containing the current courses.
     */
    public ArrayList<Course> getCurrentCourses() {
        return new ArrayList<>(currentCourses);
    }

    /**
     * Sets the list of courses the user is currently enrolled in.
     *
     * @param currentCourses The list of current courses to set.
     */
    public void setCurrentCourses(ArrayList<Course> currentCourses) {
        this.currentCourses = currentCourses != null ? new ArrayList<>(currentCourses) : new ArrayList<>();
    }

    /**
     * Gets the user's achievements.
     *
     * @return A new list containing the user's achievements.
     */
    public List<Achievements> getAchievements() {
        return new ArrayList<>(achievements);
    }

    /**
     * Sets the user's achievements.
     *
     * @param achievements The list of achievements to set.
     */
    public void setAchievements(List<Achievements> achievements) {
        this.achievements = achievements != null ? new ArrayList<>(achievements) : new ArrayList<>();
    }

    /**
     * Gets the progress per course for the user.
     *
     * @return A new map containing progress per course.
     */
    public HashMap<String, Double> getProgressPerCourse() {
        return new HashMap<>(progressPerCourse);
    }

    /**
     * Sets the progress per course for the user.
     *
     * @param progressPerCourse The map of progress per course to set.
     */
    public void setProgressPerCourse(HashMap<String, Double> progressPerCourse) {
        this.progressPerCourse = progressPerCourse != null ? new HashMap<>(progressPerCourse) : new HashMap<>();
    }

    /**
     * Gets the current course ID the user is enrolled in.
     *
     * @return The current course ID.
     */
    public String getCurrentCourseId() {
        return currentCourseId;
    }

    /**
     * Sets the current course ID the user is enrolled in.
     *
     * @param currentCourseId The course ID to set.
     */
    public void setCurrentCourseId(String currentCourseId) {
        this.currentCourseId = currentCourseId;
    }

    /**
     * Gets the current lesson ID the user is engaged with.
     *
     * @return The current lesson ID.
     */
    public String getCurrentLessonId() {
        return currentLessonId;
    }

    /**
     * Sets the current lesson ID the user is engaged with.
     *
     * @param currentLessonId The lesson ID to set.
     */
    public void setCurrentLessonId(String currentLessonId) {
        this.currentLessonId = currentLessonId;
    }

    /**
     * Gets the list of completed course IDs.
     *
     * @return A new list containing completed course IDs.
     */
    public List<String> getCompletedCourseIds() {
        return new ArrayList<>(completedCourseIds);
    }

    /**
     * Sets the list of completed course IDs.
     *
     * @param completedCourseIds The list of completed course IDs to set.
     */
    public void setCompletedCourseIds(List<String> completedCourseIds) {
        this.completedCourseIds = completedCourseIds != null ? new ArrayList<>(completedCourseIds) : new ArrayList<>();
    }

    /**
     * Gets the list of completed lesson IDs.
     *
     * @return A new list containing completed lesson IDs.
     */
    public List<String> getCompletedLessonIds() {
        return new ArrayList<>(completedLessonIds);
    }

    /**
     * Sets the list of completed lesson IDs.
     *
     * @param completedLessonIds The list of completed lesson IDs to set.
     */
    public void setCompletedLessonIds(List<String> completedLessonIds) {
        this.completedLessonIds = completedLessonIds != null ? new ArrayList<>(completedLessonIds) : new ArrayList<>();
    }

    /**
     * Gets the user's question history.
     *
     * @return A new list containing the user's question history.
     */
    public List<QuestionHistory> getQuestionHistory() {
        return new ArrayList<>(questionHistory);
    }

    /**
     * Sets the user's question history.
     *
     * @param questionHistory The list of question history to set.
     */
    public void setQuestionHistory(List<QuestionHistory> questionHistory) {
        this.questionHistory = questionHistory != null ? new ArrayList<>(questionHistory) : new ArrayList<>();
    }

    /**
     * Gets the current question the user is attempting.
     *
     * @return The current Question object.
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Sets the current question the user is attempting.
     *
     * @param currentQuestion The Question object to set as current.
     */
    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    /**
     * Authenticates the user based on provided email and password.
     *
     * @param email    The email entered by the user.
     * @param password The password entered by the user.
     * @return True if credentials match, otherwise false.
     */
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    /**
     * Updates the user's profile information.
     *
     * @param firstName The new first name.
     * @param lastName  The new last name.
     * @param email     The new email address.
     */
    public void updateProfile(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Marks a lesson as completed by adding its ID to the completed lessons list.
     *
     * @param lessonId The ID of the lesson to mark as completed.
     */
    public void markLessonAsCompleted(String lessonId) {
        if (!completedLessonIds.contains(lessonId)) {
            completedLessonIds.add(lessonId);
            System.out.println("✅ Lesson ID " + lessonId + " marked as completed.");
        }
    }

    /**
     * Marks a course as completed by adding its ID to the completed courses list,
     * removing it from current courses, and clearing its progress.
     *
     * @param courseId The ID of the course to mark as completed.
     */
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

    /**
     * Removes a course from the user's current courses by its ID.
     *
     * @param courseId The ID of the course to remove.
     */
    public void removeCourse(String courseId) {
        boolean removed = currentCourses.removeIf(course -> course.getCourseId().equals(courseId));
        if (removed) {
            progressPerCourse.remove(courseId);
            System.out.println("❌ Course ID " + courseId + " removed from current courses.");
        } else {
            System.out.println("⚠️ Course ID " + courseId + " not found in current courses.");
        }
    }

    /**
     * Calculates the overall progress of the user across all courses.
     *
     * @return The average progress percentage.
     */
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

    /**
     * Retrieves the progress percentage for a specific course.
     *
     * @param courseId The ID of the course.
     * @return The progress percentage, or 0.0 if not found.
     */
    public double getProgress(String courseId) {
        return progressPerCourse.getOrDefault(courseId, 0.0);
    }

    /**
     * Updates the user's progress for a specific course.
     *
     * @param courseId    The ID of the course.
     * @param completion  The new completion percentage.
     */
    public void updateUserProgress(String courseId, double completion) {
        // Check if the course exists in currentCourses
        boolean courseExists = currentCourses.stream()
                .anyMatch(course -> course.getCourseId().toString().equals(courseId));

        if (courseExists) {
            // Update or add the progress for the course
            progressPerCourse.put(courseId, completion);
            System.out.println("✅ Updated progress for Course ID " + courseId + " to " +
                    String.format("%.2f", completion) + "%.");
        } else {
            System.out.println("⚠️ Course ID " + courseId + " not found in currentCourses.");
        }
    }

    /**
     * Unlocks a new achievement for the user and updates the score accordingly.
     *
     * @param achievementId The unique ID of the achievement.
     * @param title         The title of the achievement.
     * @param description   The description of the achievement.
     * @param rewardPoints  The points awarded for unlocking the achievement.
     */
    public void unlockAchievement(String achievementId, String title, String description, int rewardPoints) {
        Achievements achievement = new Achievements(achievementId, title, description, rewardPoints);
        if (!achievements.contains(achievement)) {
            achievements.add(achievement);
            System.out.println("🏅 Achievement Unlocked: " + title);
            this.increaseScore(rewardPoints);
        }
    }

    /**
     * Increases the user's score by a specified increment.
     *
     * @param increment The amount to increase the score by.
     */
    public void increaseScore(double increment) {
        this.score += increment;
        System.out.println("Score increased by " + increment + ". New score: " + this.score);
    }

    /**
     * Decreases the user's score by a specified decrement.
     *
     * @param decrement The amount to decrease the score by.
     */
    public void decreaseScore(double decrement) {
        this.score -= decrement;
        System.out.println("Score decreased by " + decrement + ". New score: " + this.score);
    }

    /**
     * Generates a string representation for leaderboard display.
     *
     * @return A formatted string containing user details and score.
     */
    public String PrintLeaderboard() {
        return "USERNAME: " + this.getUserName() +
                " | NAME: " + this.getFirstName() + " " + this.getLastName() +
                " | SCORE: " + this.getScore();
    }

    /**
     * Removes a word from the list of missed words based on its ID.
     *
     * @param wordId The ID of the word to remove.
     */
    public void RemoveMissedWord(String wordName) {
        WordList wl = WordList.getInstance();
        Word word = wl.getWordByName(wordName);
        if (word != null) {
            missedWords.remove(word);
        }
    }

    /**
     * Returns a string representation of the user, including username, full name, and email.
     *
     * @return A formatted string with user details.
     */
    @Override
    public String toString() {
        return "USERNAME: " + this.getUserName() +
                " | NAME: " + this.getFirstName() + " " + this.getLastName() +
                " | EMAIL: " + this.getEmail();
    }

    /**
     * Displays the user's progress, including overall progress, completed courses and lessons,
     * current course and lesson, and missed words.
     */
    public void displayProgress() {
        System.out.println("\n=== User Progress ===");
        System.out.println("Overall Progress: " + String.format("%.2f", trackProgress()) + "%");
        System.out.println("Completed Courses: " +
                (completedCourseIds.isEmpty() ? "None" : getCourseNames(completedCourseIds)));
        System.out.println("Completed Lessons: " +
                (completedLessonIds.isEmpty() ? "None" : getLessonNames(completedLessonIds)));
        System.out.println("Current Course: " +
                (currentCourseId.isEmpty() ? "None" : getCourseName(currentCourseId)));
        System.out.println("Current Lesson: " +
                (currentLessonId.isEmpty() ? "None" : getLessonName(currentLessonId)));
        System.out.println("Missed Words:");
        if (!missedWords.isEmpty()) {
            for (Word word : missedWords) {
                System.out.println("- Word: " + word.getWord() + ", Translation: " + word.getTranslation());
            }
        } else {
            System.out.println("None");
        }
        System.out.println("=====================");
    }

    /**
     * Sends a daily reminder to the user if enabled.
     */
    public void sendDailyReminder() {
        if (dailyReminder) {
            System.out.println("⏰ Reminder: Continue your lessons to keep progressing!");
        }
    }

    /**
     * Retrieves the name of a course based on its ID.
     *
     * @param courseId The ID of the course.
     * @return The name of the course, or "Unknown Course" if not found.
     */
    private String getCourseName(String courseId) {
        Course course = CourseList.getInstance().getCourse(UUID.fromString(courseId));
        return (course != null) ? course.getCourseName() : "Unknown Course";
    }

    /**
     * Retrieves the names of multiple courses based on their IDs.
     *
     * @param courseIds The list of course IDs.
     * @return A list of course names.
     */
    private List<String> getCourseNames(List<String> courseIds) {
        List<String> courseNames = new ArrayList<>();
        for (String id : courseIds) {
            courseNames.add(getCourseName(id));
        }
        return courseNames;
    }

    /**
     * Retrieves the name of a lesson based on its ID.
     *
     * @param lessonId The ID of the lesson.
     * @return The name of the lesson, or an error message if invalid or not found.
     */
    private String getLessonName(String lessonId) {
        try {
            UUID uuid = UUID.fromString(lessonId);
            Lesson lesson = LessonList.getInstance().getLessonById(uuid);
            return (lesson != null) ? lesson.getLessonTitle() : "Unknown Lesson";
        } catch (IllegalArgumentException e) {
            // If the lessonId is not a valid UUID, return a default message
            return "Invalid Lesson ID: " + lessonId;
        }
    }

    /**
     * Retrieves the names of multiple lessons based on their IDs.
     *
     * @param lessonIds The list of lesson IDs.
     * @return A list of lesson names.
     */
    private List<String> getLessonNames(List<String> lessonIds) {
        List<String> lessonNames = new ArrayList<>();
        for (String id : lessonIds) {
            lessonNames.add(getLessonName(id));
        }
        return lessonNames;
    }
}
