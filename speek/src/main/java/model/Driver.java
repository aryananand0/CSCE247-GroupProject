package model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Driver class to simulate different scenarios for a learning app.
 * Demonstrates user registration, login, course registration, flashcard usage,
 * question generation, and user progress management.
 */
public class Driver {

    public static void main(String[] args) {
        System.out.println("--------------------------- Scenario 1 ---------------------------");
        scenario1();
        System.out.println("--------------------------- Scenario 2 ---------------------------");
        scenario2();
        System.out.println("--------------------------- Scenario 3 ---------------------------");
        scenario3();
    }

    /**
     * Scenario 1:
     * - Loads users, attempts to create duplicate users, registers a new user,
     *   logs in, logs out, verifies saved data, and logs in the new user again.
     */
    public static void scenario1() {
        LearningAppFacade facade = LearningAppFacade.getInstance();
        System.out.println("Loading users...");
        ArrayList<User> users = facade.loadUsers();

        if (!users.isEmpty()) {
            System.out.println("Users loaded successfully.");
            users.forEach(user -> System.out.println(user));
        } else {
            System.out.println("No users found.");
        }

        System.out.println("\nAttempting to create user with username 'ttomacka'...");
        if (!facade.registerUser("ttomacka", "Tim", "Tomacka", "tim.tomacka@example.com", "password123")) {
            System.out.println("User creation failed: Username 'ttomacka' already exists.");
        }

        System.out.println("\nCreating user with username 'ttom'...");
        if (facade.registerUser("ttom", "Tim", "Tomacka", "tim.tomacka@example.com", "password123")) {
            System.out.println("User 'ttom' successfully created. Logging in...");

            User user = facade.loginUser("tim.tomacka@example.com", "password123");
            if (user != null) {
                System.out.println("Login successful for 'ttom'.");
                System.out.println("\nLogging out the user...");
                if (facade.logout()) {
                    System.out.println("User successfully logged out.");
                }
            }
        }

        System.out.println("\nReloading users...");
        users = facade.loadUsers();
        users.forEach(System.out::println);

        System.out.println("\nAttempting to log in 'ttom'...");
        if (facade.loginUser("tim.tomacka@example.com", "password123") != null) {
            System.out.println("Login successful: 'ttom' logged in.");
        }
    }

    /**
     * Scenario 2:
     * - Logs in a user, registers for a course, uses flashcards, and answers questions.
     */
    public static void scenario2() {
        LearningAppFacade facade = LearningAppFacade.getInstance();
        User user = facade.loginUser("tim.tomacka@example.com", "password123");

        if (user != null) {
            System.out.println("Login successful for 'ttom'. Registering for course...");
            CourseList cl = CourseList.getInstance();
            facade.registerCourse(user.getUserId(), cl.getCourses().get(0).getCourseId());

            Course course = cl.getCourse(UUID.fromString(user.getCurrentCourseId()));
            Lesson lesson = course.getLessons().get(0);

            System.out.println("Learning with flashcards...");
            lesson.getFlashcard().showFlashcardsSequentially();

            System.out.println("\nAnswering questions...");
            QuestionManager qm = new QuestionManager(user);
            qm.generateFixedQuestionSet(lesson.getWords());

            for (int i = 0; i < qm.getAllQuestions().size(); i++) {
                Question currentQuestion = qm.getCurrentQuestion();
                String userAnswer = i < 5 ? currentQuestion.getCorrectAnswer() : "incorrectAnswer";
                System.out.println();
                System.out.println("Question " + (i + 1) + ": " + currentQuestion.display());
                System.out.println("Assigned Answer: " + userAnswer);

                if (qm.validateCurrentAnswer(userAnswer)) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect.");
                }
                qm.moveToNextQuestion();
            }
        }
    }

    /**
     * Scenario 3:
     * - Logs in an existing user, displays progress, writes missed words to file,
     *   generates questions based on missed words, and validates answers.
     */
    public static void scenario3() {
        LearningAppFacade facade = LearningAppFacade.getInstance();
        System.out.println("Loading users...");
        ArrayList<User> users = facade.loadUsers();
        users.forEach(System.out::println);

        System.out.println("\nLogging in 'ttomacka'...");
        User user = facade.loginUser("ttomacka", "hashedpassword789");

        if (user != null) {
            System.out.println("\nDisplaying user progress...");
            user.displayProgress();

            System.out.println("\nWriting missed words to file...");
            facade.writeMissedWordsToFile(user);

            System.out.println("\nGenerating questions with missed words...");
            QuestionManager qm = new QuestionManager(user);
            qm.generateFixedQuestionSet(user.getMissedWords());

            for (int i = 0; i < qm.getAllQuestions().size(); i++) {
                Question currentQuestion = qm.getCurrentQuestion();
                String userAnswer = i < 7 ? currentQuestion.getCorrectAnswer() : "incorrectAnswer";
                System.out.println();
                System.out.println("Question " + (i + 1) + ": " + currentQuestion.display());
                System.out.println("Assigned Answer: " + userAnswer);

                if (qm.validateCurrentAnswer(userAnswer)) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect.");
                }
                qm.moveToNextQuestion();
            }

            System.out.println("\nLogging out 'ttomacka'...");
            facade.logout();

            System.out.println("\nMissed Words After Questions:");
            user.getMissedWords().forEach(word -> System.out.println(word.getWord()));
        }
    }
}
