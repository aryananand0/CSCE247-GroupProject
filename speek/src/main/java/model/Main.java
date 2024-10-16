package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    /**
     * @param args
     */
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

        // addUser-valid
        // System.out.println("Adding new user - valid case...");

        // String username = "janeDoe";
        // String firstName = "Jane";
        // String lastName = "Doe";
        // String email = "janedoe@example.com";
        // String newPassword = "newpassword123";

        // boolean userAdded = userList.addUser(username, firstName, lastName, email, newPassword);

        // if (userAdded) {
        //     System.out.println("User added successfully: " + username);
        // } else {
        //     System.out.println("Failed to add user. Username already exists.");
        // }
        // System.out.println("");

        // // addUser-invalid
        // System.out.println("Adding new user - invalid case...");

        // String existingUsername = "johnDoe"; 
        // String existingFirstName = "John";
        // String existingLastName = "Doe";
        // String existingEmail = "johndoe@example.com";
        // String existingPassword = "hashedpassword123";

        // boolean invalidUserAdded = userList.addUser(existingUsername, existingFirstName, existingLastName, existingEmail, existingPassword);

        // if (invalidUserAdded) {
        //     System.out.println("User added successfully: " + existingUsername);
        // } else {
        //     System.out.println("Failed to add user. Username already exists.");
        // }
        // System.out.println("");

        // // signout.facade
        // System.out.println("Signing out...");

        // boolean logoutSuccess = LearningAppFacade.logout();

        // if (logoutSuccess) {
        //     System.out.println("User signed out successfully.");
        // } else {
        //     System.out.println("Error signing out.");
        // }
        // System.out.println("");


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
    
}