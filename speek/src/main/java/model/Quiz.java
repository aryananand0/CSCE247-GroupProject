package model;
// GOT TO CHANGE RIGHT NOW TEMP
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {

    // Attributes
    private String quizName;
    private ArrayList<Question> questions;
    private String feedback;

    // Constructor
    public Quiz(String quizName) {
        this.quizName = quizName;
        this.questions = new ArrayList<>();
        this.feedback = "";
    }

    // Method to take the quiz and return a score
    public int takeQuiz(User user) {
        int score = 0;
        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            // Display question (handles multiple-choice options too)
            System.out.println(question.display());

            // Get user input
            String userAnswer = scanner.nextLine();


            // Check if the answer is correct
            if (question.validateAnswer(userAnswer)) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect. The correct answer is: " + question.getCorrectAnswer());
            }
        }
        scanner.close();
        return score;
    }

    // Method to display feedback after taking the quiz
    public String displayFeedback() {
        return this.feedback;
    }

    // Method to add a question to the quiz
    public void addQuestion(Question question) {
        questions.add(question);
    }

    // Method to set feedback based on quiz performance
    public void setFeedback(String feedback) {
        this.feedback = feedback;
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
}


