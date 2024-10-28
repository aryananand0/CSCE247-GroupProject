package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Driver {
    public static void main(String[] args) {
        System.out.println("---------------------------Doing scenario 1---------------------------");
        scenario1();
        System.out.println("---------------------------Doing scenario 2---------------------------");
        scenario2();
        System.out.println("---------------------------Doing scenario 3---------------------------");
        scenario3();


    }

    public static void scenario1(){
        // Singleton instance of UserList to manage users
        System.out.println("Loading users...");
        ArrayList<User> users = new ArrayList<>(); 
        LearningAppFacade facade = LearningAppFacade.getInstance();
        users = facade.loadUsers();
        if (!users.isEmpty()) {
            System.out.println("Users loaded successfully.");
            for (User user : users) {
                System.out.println(user.toString());
            }
        } else {
            System.out.println("No users found.");
        }

        // 1. Attempt to create a user with the username "ttomacka" (should fail since Tammy uses it)
        System.out.println("\nAttempting to create user with username 'ttomacka'...");
        boolean isAdded = facade.registerUser("ttomacka", "Tim", "Tomacka", "tim.tomacka@example.com", "password123");

        if (!isAdded) {
            System.out.println("User creation failed: Username 'ttomacka' already exists.");
        } else {
            System.out.println("Error: User 'ttomacka' was added despite a duplicate username. Check the UserList class.");
            return;
        }

        // 4. Create a user with the username "ttom"
        System.out.println("\nCreating user with username 'ttom'...");
        isAdded = facade.registerUser("ttom", "Tim", "Tomacka", "tim.tomacka@example.com", "password123");

        if (isAdded) {
            System.out.println("User 'ttom' successfully created.");

            // Automatically log in the newly created user
            System.out.println("Automatically logging in 'ttom'...");
            User user = facade.loginUser("tim.tomacka@example.com", "password123");
            if (user != null) {
                System.out.println("Login successful: 'ttom' is now logged in.");
            } else {
                System.out.println("Error: Automatic login failed for user 'ttom'.");
                return;
            }
        } else {
            System.out.println("Error: Failed to create user 'ttom'. Check for issues in the addUser method.");
            return;
        }

        // 5. Log out the user
        System.out.println("\nLogging out the user...");
        boolean isLoggedOut = facade.logout();
        if (isLoggedOut) {
            System.out.println("User successfully logged out.");
        } else {
            System.out.println("Error: Logout failed.");
            return;
        }

        // 6. Verify that the user "ttom" is saved in users.json
        System.out.println("Loading users...");
        LearningAppFacade facades = LearningAppFacade.getInstance();
        users = facades.loadUsers();
        if (!users.isEmpty()) {
            System.out.println("Users loaded successfully.");
            for (User user : users) {
                System.out.println(user.toString());
            }
        } else {
            System.out.println("No users found.");
        }

        // 7. Log in the user "ttom" with email and password
        System.out.println("\nAttempting to log in with 'ttom'...");
        User user = facade.loginUser("tim.tomacka@example.com", "password123");

        if (user != null) {
            System.out.println("Login successful: 'ttom' logged in successfully.");
        } else {
            System.out.println("Error: Login failed for user 'ttom'.");
        }
    }

    public static void scenario2(){
        LearningAppFacade facade = LearningAppFacade.getInstance();
        //Loading in Tim
        User user = facade.loginUser("tim.tomacka@example.com", "password123");

        if (user != null) {
            System.out.println("Login successful: 'ttom' logged in successfully.");
        } else {
            System.out.println("Error: Login failed for user 'ttom'.");
        }

        // Registering Tim to the first course
        CourseList cl = CourseList.getInstance();
        // Change some of the stuff
        facade.registerCourse(user.getUserId(), cl.getCourses().get(0).getCourseId());
        Course c =cl.getCourse(UUID.fromString(user.getCurrentCourseId()));
        Lesson l = c.getLessons().get(0);
        // Learning from flashcard
        l.getFlashcard().showFlashcardsSequentially();
        // Testing knowladge

        System.out.println("Asking questions....");
        QuestionManager qm = new QuestionManager(user);
        qm.generateFixedQuestionSet(l.getWords());
        List<Question> q = qm.getAllQuestions();
        for (int i = 0; i < q.size(); i++) {
            Question currentQuestion = qm.getCurrentQuestion();
            System.out.println("Question " + (i + 1) + ":");
            System.out.println(currentQuestion.display());

            String userAnswer;
            if (i < 5) {
                // Assign correct answers
                userAnswer = currentQuestion.getCorrectAnswer();
            } else {
                // Assign made-up incorrect answers
                userAnswer = "hahah";
            }

            System.out.println("Assigned Answer: " + userAnswer);

            // Validate answer
            boolean isCorrect = qm.validateCurrentAnswer(userAnswer);
            System.out.println("Your answer is " + (isCorrect ? "correct." : "incorrect."));
            System.out.println("-------------------------");

            // Move to next question
            qm.moveToNextQuestion();
        }
    }

    public static void scenario3(){
        // Singleton instance of UserList to manage users
        System.out.println("\n---------User List---------\n");
        System.out.println("Loading users...");
        ArrayList<User> users = new ArrayList<>(); 
        LearningAppFacade facade = LearningAppFacade.getInstance();
        users = facade.loadUsers();
        if (!users.isEmpty()) {
            System.out.println("Users loaded successfully.");
            for (User user : users) {
                System.out.println(user.toString());
            }
        } else {
            System.out.println("No users found.");
        }
        // Login Tammy 
        System.out.println("\n---------Login Tammy---------\n");
        User user = facade.loginUser("ttomacka", "hashedpassword789");
        if (user != null) {
            System.out.println("Login successful: 'ttom' logged in successfully.");
        } else {
            System.out.println("Error: Login failed for user 'ttom'.");
        }
        System.out.println("\n---------Display Progress---------\n");
        user.displayProgress();
        System.out.println("\n---------Writing Progress To A File---------\n");
        facade.writeMissedWordsToFile(user);
        System.out.println("\n---------Making Questions With Missed Words---------\n");
        QuestionManager qm = new QuestionManager(user);
        qm.generateFixedQuestionSet(user.getMissedWords());
        List<Question> q = qm.getAllQuestions();
        for (int i = 0; i < q.size(); i++) {
            Question currentQuestion = qm.getCurrentQuestion();
            System.out.println("Question " + (i + 1) + ":");
            System.out.println(currentQuestion.display());

            String userAnswer;
            if (i < 7) {
                // Assign correct answers
                userAnswer = currentQuestion.getCorrectAnswer();
            } else {
                // Assign made-up incorrect answers
                userAnswer = "hahah";
            }

            System.out.println("Assigned Answer: " + userAnswer);

            // Validate answer
            boolean isCorrect = qm.validateCurrentAnswer(userAnswer);
            System.out.println("Your answer is " + (isCorrect ? "correct." : "incorrect."));
            System.out.println("-------------------------");

            // Move to next question
            qm.moveToNextQuestion();
        }
        System.out.println("\n---------LogOut Tammy---------\n");
        facade.logout();
        System.out.println("\n---------Missed Words After The Questions---------\n");
        for(Word word : user.getMissedWords()){
            System.out.println(word.getWord());
        }
    }
}
