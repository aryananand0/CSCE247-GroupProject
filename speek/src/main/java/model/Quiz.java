package model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a quiz containing a list of questions, score calculation,
 * and feedback display functionality.
 */
public class Quiz {

    private String quizName;                     // Name of the quiz
    private ArrayList<Question> questions;       // List of questions in the quiz
    private String feedback;                     // Feedback based on quiz performance

    /**
     * Constructs a Quiz with the specified name.
     *
     * @param quizName Name of the quiz.
     */
    public Quiz(String quizName) {
        this.quizName = quizName;
        this.questions = new ArrayList<>();
        this.feedback = "";
    }

    /**
     * Takes the quiz by presenting each question, gathering user answers, 
     * and calculating the score.
     *
     * @param user The User taking the quiz.
     * @return The final score of the quiz.
     */
    public int takeQuiz(User user) {
        int score = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            for (Question question : questions) {
                // Display question (handles multiple-choice options as well)
                System.out.println(question.display());

                // Get user input
                String userAnswer = scanner.nextLine().trim();

                // Check if the answer is correct
                if (question.validateAnswer(userAnswer)) {
                    score++;
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect. The correct answer is: " + question.getCorrectAnswer());
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the answer in the correct format.");
        }
        
        setFeedback(generateFeedback(score));
        return score;
    }

    /**
     * Generates feedback based on the final score.
     *
     * @param score The score achieved on the quiz.
     * @return The feedback as a string.
     */
    private String generateFeedback(int score) {
        int totalQuestions = questions.size();
        double percentage = ((double) score / totalQuestions) * 100;

        if (percentage == 100) {
            return "Excellent! You got all answers correct!";
        } else if (percentage >= 80) {
            return "Great job! You scored above 80%. Keep it up!";
        } else if (percentage >= 50) {
            return "Good effort. Review the material and try again.";
        } else {
            return "Needs improvement. Consider revisiting the topics and retaking the quiz.";
        }
    }

    /**
     * Displays the feedback after taking the quiz.
     *
     * @return The feedback message.
     */
    public String displayFeedback() {
        return this.feedback;
    }

    // Methods for managing questions

    /**
     * Adds a question to the quiz.
     *
     * @param question The Question object to add.
     */
    public void addQuestion(Question question) {
        questions.add(question);
    }

    // Getters and Setters

    public String getQuizName() {
        return this.quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
