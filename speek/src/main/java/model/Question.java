package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {
    // Attributes
    private String question;
    private QuestionType questionType;
    private ArrayList<String> options;
    private String answer;
    private ArrayList<String> correctAnswerList;
    private HashMap<User, String> userAnswers;

    // Constructor with question, answer, and questionType
    public Question(String question, String answer, QuestionType questionType) {
        this.question = question;
        this.answer = answer;
        this.questionType = questionType;
        this.options = new ArrayList<>();
        this.correctAnswerList = new ArrayList<>();
        this.userAnswers = new HashMap<>();
    }

    // Constructor with question, options, answer, and questionType
    public Question(String question, ArrayList<String> options, String answer, QuestionType questionType) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.questionType = questionType;
        this.correctAnswerList = new ArrayList<>();
        this.userAnswers = new HashMap<>();
    }

    // Constructor with options, answers, and questionType
    public Question(ArrayList<String> questionOption, ArrayList<String> questionAns, HashMap<String, String> answer, QuestionType questionType) {
        this.options = questionOption;
        this.correctAnswerList = questionAns;
        this.userAnswers = answer;
        this.questionType = questionType;
    }

    // Method to check user's answer
    public boolean checkAnswer(User user) {
        // Simple check logic
        return userAnswers.containsKey(user.getName()) && userAnswers.get(user.getName()).equals(answer);
    }

    // Method to display the question
    public String displayQuestion(User user) {
        return question;
    }

    // Add multiple-choice options
    public void addMultipleChoiceOptions(ArrayList<String> options) {
        this.options = options;
    }

    // Set true/false answer
    public void setTrueFalseAnswer(String correctAnswer) {
        this.answer = correctAnswer;
    }

    // Set short answer
    public void setShortAnswer(String correctAnswer) {
        this.answer = correctAnswer;
    }

    // Set "Match the Words" answer
    public void setMatchTheWords(ArrayList<String> correctAnswerList) {
        this.correctAnswerList = correctAnswerList;
    }

    // Store user's answer
    public void storeUserAnswer(User user, String answer) {
        userAnswers.put(user.getName(), answer);
    }

    // Get user's progress
    public double getUserProgress(User user) {
        // Simple return for demonstration
        return 0.0;  // Placeholder return
    }
}

