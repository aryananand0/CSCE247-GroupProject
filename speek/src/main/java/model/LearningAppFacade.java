package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
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
    public void registerCourse(UUID u, UUID courseId) {
        user = UserList.getInstance();
        // ArrayList<Course> c = user.getUserById(u).getCurrentCourses();
        // for (Course course : c) {
        //     System.out.println(course.getCourseName());
        // }
        user.getUserById(u).getCurrentCourses().add(courses.getCourse(courseId));  
        user.getUserById(u).setCurrentCourseId(courseId.toString());
        // logout();
        // System.out.println("New ones: \n");
        // for (Course course : c) {
        //     System.out.println(course.getCourseName());
        // }
    }

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
        if (currentUser != null) {
            // Save user data before logging out
            ArrayList<User> userList = new ArrayList<>();
            userList.add(currentUser);
            DataWriter.saveUsers(userList);  // Save the current user's data to user.json

            currentUser = null;
            return user.logout();  // Call the UserList's logout method
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

    public  void writeMissedWordsToFile(User user) {
        // Validate user
        if (user == null) {
            System.err.println("Error: User is null.");
            return;
        }

        // Retrieve user's first and last name
        String firstName = user.getFirstName() != null ? user.getFirstName().trim() : "Unknown";
        String lastName = user.getLastName() != null ? user.getLastName().trim() : "User";

        // Generate a safe file name
        String fileName = generateFileName(firstName, lastName) + "_MissedWords.txt";

        // Define the directory to store missed words files
        String directoryPath = "missed_words"; // You can change this to your desired directory
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                System.err.println("Error: Failed to create directory for missed words.");
                return;
            }
        }

        // Complete file path
        String filePath = directoryPath + File.separator + fileName;

        // Retrieve missed words
        List<Word> missedWords = user.getMissedWords();

        // Handle case with no missed words
        if (missedWords == null || missedWords.isEmpty()) {
            String noMissedWordsContent = String.format("Missed Words for %s %s\n\nYou have no missed words. Great job!",
                    firstName, lastName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(noMissedWordsContent);
                System.out.println("Missed words file created successfully: " + filePath);
            } catch (IOException e) {
                System.err.println("Error writing missed words to file: " + e.getMessage());
            }
            return;
        }

        // Start building the content
        StringBuilder contentBuilder = new StringBuilder();

        // Header
        contentBuilder.append(String.format("Missed Words for %s %s\n\n", firstName, lastName));

        // Table headers
        String leftAlignFormat = "| %-3s | %-15s | %-20s |%n";
        String separator = "+-----+-----------------+----------------------+%n";

        contentBuilder.append(String.format(separator));
        contentBuilder.append(String.format(leftAlignFormat, "No.", "Word", "Translation"));
        contentBuilder.append(String.format(separator));

        // Table rows
        int count = 1;
        for (Word word : missedWords) {
            String wordText = word.getWord() != null ? word.getWord() : "N/A";
            String translation = word.getTranslation() != null ? word.getTranslation() : "N/A";
            contentBuilder.append(String.format(leftAlignFormat, count, wordText, translation));
            count++;
        }

        // Footer
        contentBuilder.append(String.format(separator));

        // Write to file
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
    private  String generateFileName(String firstName, String lastName) {
        String fullName = firstName + " " + lastName;
        // Normalize to remove accents and other diacritics
        String normalized = Normalizer.normalize(fullName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        // Replace spaces and other non-alphanumeric characters with underscores
        String sanitized = normalized.replaceAll("[^a-zA-Z0-9]", "_");
        return sanitized;
    }
}
