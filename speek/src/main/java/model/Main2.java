package model;

import java.util.List;
import java.util.UUID;

public class Main2 {
    public static void main(String[] args) {
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
        // l.getFlashcard().showFlashcardsTimed();
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
}
