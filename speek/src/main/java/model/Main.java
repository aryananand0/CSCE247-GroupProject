package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        System.out.println("");
        
        // Load and display users
        ArrayList<User> users = loadAndDisplayUsers();

        // Add new user if not already exists
        addUserIfNotExists(users);

        // Reload and display users
        reloadAndDisplayUsers();

        // Load and display courses
        loadAndDisplayCourses();

        // Load and display languages
        loadAndDisplayLanguages();

        // Load and display leaderboard
        loadAndDisplayLeaderboard();

        // Load and display achievements
        loadAndDisplayAchievements();

        // Perform login scenarios
        performLoginScenarios();

        // Load question class and lessons
        loadQuestionClass();
        loadLessons();

        // Print summary report
        printSummary(users);
    }

    private static ArrayList<User> loadAndDisplayUsers() {
        System.out.println("Loading users...");
        ArrayList<User> users = new ArrayList<>();
        
        try {
            // TODO: 
            users = DataLoader.loadUsers();
            if (!users.isEmpty()) {
                System.out.println("Users loaded successfully.");
                for (User user : users) {
                    System.out.println(user.toString());
                }
            } else {
                System.out.println("No users found.");
            }
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        
        return users;
    }

    private static void addUserIfNotExists(ArrayList<User> users) {
        String newUserEmail = "alicesmith@example.com";
        boolean userExists = users.stream().anyMatch(user -> user.getEmail().equals(newUserEmail));

        if (!userExists) {
            System.out.println("Adding new user...");
            User newUser = new User("jakeadams123", "Jake", "adams", newUserEmail, "password123");
            users.add(newUser);
            DataWriter.saveUsers(users);
            System.out.println("New user added and users saved to JSON file.");
        } else {
            System.out.println("User already exists, skipping addition.");
        }
    }

    private static void reloadAndDisplayUsers() {
        System.out.println("\nReloaded users from file:");
        // TODO: 
        ArrayList<User> reloadedUsers = DataLoader.loadUsers();
        for (User user : reloadedUsers) {
            System.out.println(user.toString());
        }
        System.out.println("");
    }

    private static void loadAndDisplayCourses() {
        System.out.println("Loading courses...");
        ArrayList<Course> courses = new ArrayList<>();
        
        try {
            // TODO: 
            courses = DataLoader.loadCourses();
            if (!courses.isEmpty()) {
                System.out.println("Courses loaded successfully.");
                for (Course course : courses) {
                    System.out.println(course.toString());
                }
            } else {
                System.out.println("No courses found.");
            }
        } catch (Exception e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
        System.out.println("");
    }

    private static void loadAndDisplayLanguages() {
        System.out.println("Loading languages...");
        ArrayList<Language> languages = new ArrayList<>();
        
        try {
            // TODO: 
            languages = DataLoader.loadLanguages();
            if (!languages.isEmpty()) {
                System.out.println("Languages loaded successfully.");
                for (Language language : languages) {
                    System.out.println(language.toString());
                }
            } else {
                System.out.println("No languages found.");
            }
        } catch (Exception e) {
            System.out.println("Error loading languages: " + e.getMessage());
        }
        System.out.println("");
    }

    private static void loadAndDisplayLeaderboard() {
        System.out.println("Loading Leader Board...");
        Leaderboard lead = new Leaderboard();
        
        try {
            // TODO:
            lead = DataLoader.loadLeaderboard();
            if (!lead.getUser().isEmpty()) {
                System.out.println("Leader Board loaded successfully.");
                for (User user : lead.getUser()) {
                    System.out.println(user.PrintLeaderboard());
                }
            } else {
                System.out.println("No Leader Board found.");
            }
        } catch (Exception e) {
            System.out.println("Error loading Leader Board: " + e.getMessage());
        }
        System.out.println("");
    }

    private static void loadAndDisplayAchievements() {
        System.out.println("Loading Achievements...");
        ArrayList<Achievements> achievements = new ArrayList<>();
        
        try {
            // TODO:
            achievements = DataLoader.loadAchievements();
            if (!achievements.isEmpty()) {
                System.out.println("Achievements loaded successfully.");
                for (Achievements achiv : achievements) {
                    System.out.println(achiv.toString());
                }
            } else {
                System.out.println("No Achievements found.");
            }
        } catch (Exception e) {
            System.out.println("Error loading Achievements: " + e.getMessage());
        }
        System.out.println("");
    }

    private static void performLoginScenarios() {
        LearningAppFacade appFacade = LearningAppFacade.getInstance();

        // Valid login attempt
        System.out.println("Attempting valid login...");
        String usernameOrEmail = "johndoe@example.com"; 
        String password = "hashedpassword123";
        User loggedInUser = appFacade.loginUser(usernameOrEmail, password);
        displayLoginResult(usernameOrEmail, loggedInUser);

        // Invalid login attempt
        System.out.println("Attempting invalid login...");
        String invalidUsernameOrEmail = "johnDoe";
        String invalidPassword = "wrongPassword";
        User invalidLoginUser = appFacade.loginUser(invalidUsernameOrEmail, invalidPassword);
        displayLoginResult(invalidUsernameOrEmail, invalidLoginUser);

        // Signout scenario
        System.out.println("Signing out...");
        boolean logoutSuccess = appFacade.logout();
        if (logoutSuccess) {
            System.out.println("Sign out successful for " + usernameOrEmail);
        } else {
            System.out.println("Error signing out.");
        }
        System.out.println("");
    }

    private static void displayLoginResult(String usernameOrEmail, User user) {
        if (user != null) {
            System.out.println("Login successful for: " + usernameOrEmail);
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
        System.out.println("");
    }

    private static void loadQuestionClass() {
        System.out.println("Loading Question class stuff...");
        testingQuestionClass();
        System.out.println("");
    }

    private static void loadLessons() {
        System.out.println("Loading Lessons...\n");
        ShowLessons();
    }

    public static void testingQuestionClass() {
        // Initialize QuestionManager
        QuestionManager qm = new QuestionManager();

        // 1. Multiple Choice Question
        List<String> mcOptions = Arrays.asList("Berlin", "London", "Paris", "Madrid");
        Question mcq = new MultipleChoiceQuestion(
                "What is the capital of France?",
                mcOptions,
                "Paris"
        );

        // 2. True/False Question
        Question tfq = new TrueFalseQuestion(
                "The sky is green.",
                false
        );

        // 3. Short Answer Question
        Question saq = new ShortAnswerQuestion(
                "Translate 'Hello' to Spanish.",
                "Hola"
        );

        // 4. Match Words Question
        List<String> prompts = Arrays.asList("Apple", "Book", "Cat");
        List<String> responses = Arrays.asList("Manzana", "Libro", "Gato");
        Map<String, String> matches = new HashMap<>();
        matches.put("Apple", "Manzana");
        matches.put("Book", "Libro");
        matches.put("Cat", "Gato");
        MatchWordsQuestion mwq = new MatchWordsQuestion(
                "Match the English words to their Spanish translations.",
                prompts,
                responses,
                matches
        );

        // Add questions to the manager
        qm.addQuestion(mcq);
        qm.addQuestion(tfq);
        qm.addQuestion(saq);
        qm.addQuestion(mwq);

        // Simulate user answers
        simulateUserAnswers(qm, mcq, tfq, saq, mwq);
    }

    private static void simulateUserAnswers(QuestionManager qm, Question mcq, Question tfq, Question saq, Question mwq) {
        // Answering Multiple Choice Question
        String userAnswerMCQ = "3"; // Selecting "Paris"
        System.out.println(mcq.display());
        System.out.println("Your answer: " + userAnswerMCQ);
        System.out.println(qm.getAnswerText(mcq, userAnswerMCQ));

        // Answering True/False Question
        String userAnswerTFQ = "False";
        System.out.println(tfq.display());
        System.out.println("Your answer: " + userAnswerTFQ);
        System.out.println(qm.getAnswerText(tfq, userAnswerTFQ));

        // Answering Short Answer Question
        String userAnswerSAQ = "Hola";
        System.out.println(saq.display());
        System.out.println("Your answer: " + userAnswerSAQ);
        System.out.println(qm.getAnswerText(saq, userAnswerSAQ));

        // Answering Match Words Question
        String userAnswerMWQ = "1-1,2-2,3-3"; // Correct matching
        System.out.println(mwq.display());
        System.out.println("Your answer: " + userAnswerMWQ);
        System.out.println(qm.getAnswerText(mwq, userAnswerMWQ));
    }

    public static void ShowLessons() {
        // Load courses from the JSON file
        // TODO:
        ArrayList<Course> courses = DataLoader.loadCoursesFromJson();

        // Check if courses were loaded successfully
        if (courses.isEmpty()) {
            System.out.println("No courses loaded. Please check your JSON data and file path.");
            return;
        }

        // Display the loaded courses
        for (Course course : courses) {
            System.out.println("\n=== " + course.getCourseName() + " ===");
            System.out.println("Course ID: " + course.getCourseId());
            System.out.println("Difficulty: " + course.getDifficulty());
            System.out.println("Completion: " + course.getCourseCompletion() + "%");

            // Iterate through lessons
            for (Lesson lesson : course.getLessons()) {
                System.out.println("\n  --- " + lesson.getLessonTitle() + " ---");
                System.out.println("  Lesson ID: " + lesson.getLessonId());
                System.out.println("  Content:\n" + lesson.getContent());

                System.out.println("\nQuestions:\n");
                for (Question question : lesson.getQuestions()) {
                    System.out.println("Question ID: " + question.getId());
                    System.out.println("Type: " + question.getType());
                    System.out.println(question.display());
                    System.out.println("Correct Answer: " + question.getCorrectAnswer());
                    System.out.println();
                }
            }
        }
    }

    private static void printSummary(ArrayList<User> users) {
        // TODO:
        System.out.println("\n=== Summary Report ===");
        System.out.println("Total users loaded: " + users.size());
        System.out.println("Total courses loaded: " + DataLoader.loadCourses().size());
        System.out.println("Total languages loaded: " + DataLoader.loadLanguages().size());
        System.out.println("Total achievements loaded: " + DataLoader.loadAchievements().size());
        System.out.println("Completed successfully.");
        System.out.println("======================");
    }
}

