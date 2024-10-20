package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        System.out.println("");
        
        // Loading users
        System.out.println("Loading users...");
        ArrayList<User> users = DataLoader.loadUsers();
        
        if (!users.isEmpty()) {
            System.out.println("Users loaded successfully.");
            for (User user : users) {
                System.out.println(user.toString());
            }
        } else {
            System.out.println("No users found.");
        }
        
        // Check if Alice Smith already exists
        String newUserEmail = "alicesmith@example.com";
        boolean userExists = false;
        
        for (User user : users) {
            if (user.getEmail().equals(newUserEmail)) {
                userExists = true;
                break;
            }
        }
        
        // Add new user only if they don't already exist
        if (!userExists) {
            System.out.println("Adding new user...");
            User newUser = new User("jakeadams123", "Jake", "adams", newUserEmail, "password123");
            users.add(newUser);
            
            // Save users to file
            DataWriter.saveUsers(users);
            System.out.println("New user added and users saved to JSON file.");
        } else {
            System.out.println("User already exists, skipping addition.");
        }
        
        System.out.println("\nReloaded users from file:");
        ArrayList<User> reloadedUsers = DataLoader.loadUsers();
        for (User user : reloadedUsers) {
            System.out.println(user.toString());
        }
        
        System.out.println("");
        
        // Loading courses
        System.out.println("Loading courses...");
        ArrayList<Course> courses = DataLoader.loadCourses();
        
        if (!courses.isEmpty()) {
            System.out.println("Courses loaded successfully.");
            for (Course course : courses) {
                System.out.println(course.toString());
            }
        } else {
            System.out.println("No courses found.");
        }
        
        System.out.println("");

        // Loading languages
        System.out.println("Loading languages...");
        ArrayList<Language> languages = DataLoader.loadLanguages();
        
        if (!languages.isEmpty()) {
            System.out.println("Languages loaded successfully.");
            for (Language language : languages) {
                System.out.println(language.toString());
            }
        } else {
            System.out.println("No languages found.");
        }

        System.out.println("");

        // Loading Leader Board
        System.out.println("Loading Leader Board...");
        Leaderboard lead = DataLoader.loadLeaderboard();
        
        if (!lead.getUser().isEmpty()) {
            System.out.println("Leader Board loaded successfully.");
            for (User user : lead.getUser()) {
                System.out.println(user.PrintLeaderboard());
            }
        } else {
            System.out.println("No Leader Board found.");
        }

        System.out.println("");

        // Loading Achievements
        System.out.println("Loading Achievements...");

        ArrayList<Achievements> achievements = DataLoader.loadAchievements();
        
        if (!achievements.isEmpty()) {
            System.out.println("Achievements loaded successfully.");
            for (Achievements achiv : achievements) {
                System.out.println(achiv.toString());
            }
        } else {
            System.out.println("No Achievements found.");
        }
        System.out.println("");
        
        // Login scenarios
        System.out.println("Attempting valid login...");
        LearningAppFacade appFacade = LearningAppFacade.getInstance();

        String usernameOrEmail = "johndoe@example.com"; 
        String password = "hashedpassword123";

        User loggedInUser = appFacade.loginUser(usernameOrEmail, password);

        if (loggedInUser != null) {
            System.out.println("Login successful for: " + usernameOrEmail);
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
        System.out.println("");

        // Check invalid login
        System.out.println("Attempting invalid login...");

        String invalidUsernameOrEmail = "johnDoe";
        String invalidPassword = "wrongPassword";

        User invalidLoginUser = appFacade.loginUser(invalidUsernameOrEmail, invalidPassword);

        if (invalidLoginUser != null) {
            System.out.println("Login successful for: " + invalidUsernameOrEmail);
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
        System.out.println("");


        // Signout scenario
        System.out.println("Signing out...");

        boolean logoutSuccess = appFacade.logout();

        if (logoutSuccess) {
            System.out.println("Sign out successful for " + usernameOrEmail);
         } else {
            System.out.println("Error signing out.");
        }
        System.out.println("");


        System.out.println("Loading Question class stuff...");
        testingQuestionClass();
        System.out.println("");
        System.out.println("Loading Lessons...\n");
        ShowLessons();
    }

    public static void testingQuestionClass(){
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
        // In a real application, these would come from user input

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
        // Display all questions (optional)
        // qm.displayAllQuestions();
    }
    
    public static void ShowLessons(){
        // Specify the path to your JSON file
        // Load courses from the JSON file
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
                System.out.println("Question ID: "+question.getId());
                System.out.println("Type: " + question.getType());
                System.out.println(question.display());
                System.out.println("Correct Answer: " + question.getCorrectAnswer());
                System.out.println();
            }
            }
        
}
}
}