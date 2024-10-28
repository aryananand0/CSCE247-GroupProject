package model;

import java.util.ArrayList;
import java.util.List;

public class main3A {
    public static void main(String[] args) {
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
