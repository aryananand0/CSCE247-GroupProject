package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {

    // Attributes
    private String questionText;
    private QuestionType questionType;  // Enum or class to define question types (MultipleChoice, TrueFalse, ShortAnswer, etc.)
    private ArrayList<String> options;  // For multiple-choice questions
    private String correctAnswer;
    private HashMap<User, String> userAnswers;  // Stores user answers to this question

    // Constructor for basic questions (without options, like True/False or Short Answer)
    public Question(String questionText, String correctAnswer, QuestionType questionType) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
        this.options = new ArrayList<>();
        this.userAnswers = new HashMap<>();
    }

    // Constructor for multiple-choice questions
    public Question(String questionText, ArrayList<String> options, String correctAnswer, QuestionType questionType) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
        this.userAnswers = new HashMap<>();
    }

    // Method to check if a user's answer is correct
    public boolean checkAnswer(User user) {
        String userAnswer = userAnswers.get(user);
        return userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer);
    }

    // Method to display the question (includes options for multiple-choice questions)
    public String displayQuestion() {
        StringBuilder questionDisplay = new StringBuilder();
        questionDisplay.append("Question: ").append(questionText).append("\n");

        if (!options.isEmpty()) {
            questionDisplay.append("Options:\n");
            for (int i = 0; i < options.size(); i++) {
                questionDisplay.append((i + 1) + ". " + options.get(i)).append("\n");
            }
        }
        return questionDisplay.toString();
    }

    // Method to store a user's answer
    public void storeUserAnswer(User user, String answer) {
        userAnswers.put(user, answer);
    }

    // Getters and Setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public HashMap<User, String> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(HashMap<User, String> userAnswers) {
        this.userAnswers = userAnswers;
    }
}