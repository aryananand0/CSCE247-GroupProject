package model;

public class QuestionHistory {
    private String questionId;
    private String questionText;
    private String userAnswer;
    private String correctAnswer;
    private boolean isCorrect;

    // Constructors
    public QuestionHistory(String questionId, String questionText, String userAnswer, String correctAnswer, boolean isCorrect) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.isCorrect = isCorrect;
    }

    public QuestionHistory() {
        this.questionId = "";
        this.questionText = "";
        this.userAnswer = "";
        this.correctAnswer = "";
        this.isCorrect = false;
    }

    // Getters and Setters

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    // Overriding toString() method
    @Override
    public String toString() {
        return "QuestionHistory{" +
                "questionId='" + questionId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}

