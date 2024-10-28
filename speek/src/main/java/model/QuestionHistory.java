package model;

/**
 * Represents the history of a question attempt, including the question details,
 * user's answer, correct answer, and whether the user's answer was correct.
 */
public class QuestionHistory {
    private String questionId;
    private String questionText;
    private String userAnswer;
    private String correctAnswer;
    private boolean isCorrect;

    /**
     * Constructs a QuestionHistory object with specified details.
     *
     * @param questionId    The unique ID of the question.
     * @param questionText  The text of the question.
     * @param userAnswer    The user's answer.
     * @param correctAnswer The correct answer.
     * @param isCorrect     True if the user's answer was correct, otherwise false.
     */
    public QuestionHistory(String questionId, String questionText, String userAnswer, String correctAnswer, boolean isCorrect) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.isCorrect = isCorrect;
    }

    /**
     * Default constructor initializing fields with default values.
     */
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

    /**
     * Provides a string representation of the question history.
     *
     * @return A string with question details and answer evaluation.
     */
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
