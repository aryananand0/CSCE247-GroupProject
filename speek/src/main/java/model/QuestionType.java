package model;

public interface QuestionType {
    public String getAnswer();
    public String getQuestion();
    public String toString();
    public boolean isCorrect(String UserAnswer);
}
