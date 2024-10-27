package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TestNewWordQuestions {
    public static void main(String[] args) {
        LearningAppFacade facade = LearningAppFacade.getInstance();
        ArrayList<Course> courses = facade.loadCourses();
        Lesson lesson = courses.get(0).getLessons().get(2);
        System.out.println("\nLoaded Lesson: " + lesson.getLessonTitle());
        System.out.println("Generating questions...\n");

        // Initialize QuestionManager with the User
        User user = new User("jakeadams123", "Jake", "adams", "newUserEmail", "password123");
        QuestionManager qm = new QuestionManager(user);

        // =========================
        // Example 1: Fixed 7 Questions
        // =========================
        System.out.println("=== Quiz with Fixed 7 Questions ===\n");
        try {
            qm.generateFixedQuestionSet(lesson.getWords());

            List<Question> fixedQuestions = qm.getAllQuestions();

            // Simulate user answers: Assign correct answers to first 5, incorrect to last 2
            for (int i = 0; i < fixedQuestions.size(); i++) {
                Question currentQuestion = qm.getCurrentQuestion();
                System.out.println("Question " + (i + 1) + ":");
                System.out.println(currentQuestion.display());

                String userAnswer;
                if (i < 5) {
                    // Assign correct answers
                    userAnswer = currentQuestion.getCorrectAnswer();
                } else {
                    // Assign made-up incorrect answers
                    userAnswer =currentQuestion.getCorrectAnswer();
                }

                System.out.println("Assigned Answer: " + userAnswer);

                // Validate answer
                boolean isCorrect = qm.validateCurrentAnswer(userAnswer);
                System.out.println("Your answer is " + (isCorrect ? "correct." : "incorrect."));
                System.out.println("-------------------------");

                // Move to next question
                qm.moveToNextQuestion();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error generating fixed question set: " + e.getMessage());
        }

        // Reset for the next example
        user.clearMissedWords();
        qm = new QuestionManager(user);

        // =========================
        // Example 2: Dynamic Questions as per Words
        // =========================
        System.out.println("\n=== Quiz with Dynamic Questions as per Words ===\n");
        try {
            qm.generateQuestionsAsPerWords(lesson.getWords());

            List<Question> dynamicQuestions = qm.getAllQuestions();

            // Define the number of correct and incorrect answers
            // We'll assign correct answers to 5 questions and incorrect to 2, as before
            int correctAnswersCount = 5;
            int incorrectAnswersCount = 2;

            // Initialize counters
            int correctAssigned = 0;
            int incorrectAssigned = 0;

            // Iterate through all questions and present them with dynamic answers
            for (int i = 0; i < dynamicQuestions.size(); i++) {
                Question currentQuestion = qm.getCurrentQuestion();
                System.out.println("Question " + (i + 1) + ":");
                System.out.println(currentQuestion.display());

                String userAnswer;

                if (correctAssigned < correctAnswersCount) {
                    // Assign the correct answer using getCorrectAnswer()
                    userAnswer = currentQuestion.getCorrectAnswer();
                    correctAssigned++;
                } else if (incorrectAssigned < incorrectAnswersCount) {
                    // Assign a made-up incorrect answer
                    userAnswer = currentQuestion.getCorrectAnswer();
                    incorrectAssigned++;
                } else {
                    // For any additional questions beyond 7, assign correct answers
                    userAnswer = currentQuestion.getCorrectAnswer();
                }

                System.out.println("Assigned Answer: " + userAnswer);

                // Validate answer
                boolean isCorrect = qm.validateCurrentAnswer(userAnswer);
                System.out.println("Your answer is " + (isCorrect ? "correct." : "incorrect."));
                System.out.println("-------------------------");

                // Move to next question
                qm.moveToNextQuestion();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error generating dynamic question set: " + e.getMessage());
        }

        // Retrieve the list of wrong words from the User
        List<Word> wrongWords = user.getMissedWords();
        if (!wrongWords.isEmpty()) {
            System.out.println("\nYou got the following words wrong:");
            for (Word word : wrongWords) {
                System.out.println("- " + word.getWord() + " -> " + word.getTranslation());
            }
            // Save wrongWords to a file
        } else {
            System.out.println("\nGreat job! You answered all questions correctly.");
        }



    }

    }

