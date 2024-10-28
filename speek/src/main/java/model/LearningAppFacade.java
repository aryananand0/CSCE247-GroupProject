package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Facade class for managing user interactions within the learning application.
 * Provides methods for user registration, login, course management, achievement tracking, 
 * and writing missed words to a file.
 */
public class LearningAppFacade {

    private ArrayList<Language> languages;
    private UserList user;
    private CourseList courses;
    private LessonList lessons;
    private User currentUser; // Store the current logged-in user
    private static LearningAppFacade instance; // Singleton instance
    private AchievementList achievements;
    private LeaderboardList leaderboardList;

    /**
     * Private constructor for singleton pattern.
     */
    private LearningAppFacade() {
        user = UserList.getInstance();
        courses = CourseList.getInstance();
        lessons = LessonList.getInstance();
        languages = DataLoader.loadLanguages();
        achievements = AchievementList.getInstance();
        leaderboardList = LeaderboardList.getInstance();
    }

    /**
     * Retrieves the singleton instance of LearningAppFacade.
     *
     * @return The singleton instance of LearningAppFacade.
     */
    public static LearningAppFacade getInstance() {
        if (instance == null) {
            instance = new LearningAppFacade();
        }
        return instance;
    }

    /**
     * Registers a user in a course.
     *
     * @param u        The UUID of the user.
     * @param courseId The UUID of the course.
     */
    public void registerCourse(UUID u, UUID courseId) {
        user.getUserById(u).getCurrentCourses().add(courses.getCourse(courseId));
        user.getUserById(u).setCurrentCourseId(courseId.toString());
        logout();
    }

    /**
     * Retrieves questions associated with a specific lesson.
     *
     * @param lesson The UUID of the lesson.
     * @return A list of questions for the given lesson.
     */
    public ArrayList<Question> getQuestions(UUID lesson) {
        return new ArrayList<>(lessons.getQuestions(lesson));
    }

    /**
     * Registers a new user with the specified details.
     *
     * @param username  The username of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user.
     * @param password  The password for the user.
     * @return true if registration was successful; false otherwise.
     */
    public boolean registerUser(String username, String firstName, String lastName, String email, String password) {
        return user.addUser(username, firstName, lastName, email, password);
    }

    /**
     * Loads the list of registered users.
     *
     * @return A list of users.
     */
    public ArrayList<User> loadUsers() {
        return this.user.getUsers();
    }

    /**
     * Loads available courses.
     *
     * @return A list of courses.
     */
    public ArrayList<Course> loadCourses() {
        return this.courses.getCourses();
    }

    /**
     * Loads available lessons.
     *
     * @return A list of lessons.
     */
    public ArrayList<Lesson> loadLesson() {
        return this.lessons.getLessons();
    }

    /**
     * Loads languages.
     *
     * @return A list of languages.
     */
    public ArrayList<Language> loadLanguages() {
        return this.languages;
    }

    /**
     * Loads achievements.
     *
     * @return A list of achievements.
     */
    public ArrayList<Achievements> loadAchievements() {
        return this.achievements.getAchievements();
    }

    /**
     * Loads the leaderboard.
     *
     * @return The leaderboard.
     */
    public Leaderboard loadLeaderboard() {
        return this.leaderboardList.getLeaderboard();
    }

    /**
     * Logs in a user with either their username or email and password.
     *
     * @param usernameOrEmail The username or email of the user.
     * @param password        The user's password.
     * @return The logged-in User object or null if login fails.
     */
    public User loginUser(String usernameOrEmail, String password) {
        if (user.loginCheck(usernameOrEmail, password)) {
            currentUser = user.getUser(usernameOrEmail, password);
            return currentUser;
        }
        return null;
    }

    /**
     * Retrieves the current logged-in user.
     *
     * @return The current user, or null if no user is logged in.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logs out the current user, saving their data and clearing the currentUser field.
     *
     * @return true if logout was successful; false otherwise.
     */
    public boolean logout() {
        if (currentUser != null) {
            ArrayList<User> userList = new ArrayList<>();
            userList.add(currentUser);
            user.logout();
            currentUser = null;
            return user.logout();
        }
        return false;
    }

    /**
     * Writes the missed words for a user to a file in a structured format.
     *
     * @param user The user whose missed words are to be written to the file.
     */
    public void writeMissedWordsToFile(User user) {
        if (user == null) {
            System.err.println("Error: User is null.");
            return;
        }

        String firstName = user.getFirstName() != null ? user.getFirstName().trim() : "Unknown";
        String lastName = user.getLastName() != null ? user.getLastName().trim() : "User";
        String fileName = generateFileName(firstName, lastName) + "_MissedWords.txt";

        String directoryPath = "missed_words";
        File directory = new File(directoryPath);
        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("Error: Failed to create directory for missed words.");
            return;
        }

        String filePath = directoryPath + File.separator + fileName;
        List<Word> missedWords = user.getMissedWords();

        if (missedWords == null || missedWords.isEmpty()) {
            String noMissedWordsContent = String.format("Missed Words for %s %s\n\nYou have no missed words. Great job!", firstName, lastName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(noMissedWordsContent);
                System.out.println("Missed words file created successfully: " + filePath);
            } catch (IOException e) {
                System.err.println("Error writing missed words to file: " + e.getMessage());
            }
            return;
        }

        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append(String.format("Missed Words for %s %s\n\n", firstName, lastName));
        String leftAlignFormat = "| %-3s | %-15s | %-20s |%n";
        String separator = "+-----+-----------------+----------------------+%n";

        contentBuilder.append(String.format(separator));
        contentBuilder.append(String.format(leftAlignFormat, "No.", "Word", "Translation"));
        contentBuilder.append(String.format(separator));

        int count = 1;
        for (Word word : missedWords) {
            String wordText = word.getWord() != null ? word.getWord() : "N/A";
            String translation = word.getTranslation() != null ? word.getTranslation() : "N/A";
            contentBuilder.append(String.format(leftAlignFormat, count, wordText, translation));
            count++;
        }

        contentBuilder.append(String.format(separator));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(contentBuilder.toString());
            System.out.println("Missed words file created successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing missed words to file: " + e.getMessage());
        }
    }

    /**
     * Generates a safe file name by removing special characters and replacing spaces with underscores.
     *
     * @param firstName The user's first name.
     * @param lastName  The user's last name.
     * @return A sanitized file name.
     */
    private String generateFileName(String firstName, String lastName) {
        String fullName = firstName + " " + lastName;
        String normalized = Normalizer.normalize(fullName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        return normalized.replaceAll("[^a-zA-Z0-9]", "_");
    }
}
