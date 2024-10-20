package model;

import java.util.UUID;

public class TrueFalseQuestion extends Question {
    private boolean correctAnswer; 
    private UUID id;

    
    public TrueFalseQuestion(UUID id,String text, boolean correctAnswer) {
        super(text);
        this.id = id;
        this.correctAnswer = correctAnswer;
    }

    public TrueFalseQuestion(String text, boolean correctAnswer) {
        super(text);
        this.id = UUID.randomUUID();
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String display() {
        return getText() + " (True/False)";
    }

    @Override
    public boolean validateAnswer(String userAnswer) {
        if (userAnswer == null) return false;
        String normalized = userAnswer.trim().toLowerCase();
        if (normalized.equals("true") || normalized.equals("t")) {
            return correctAnswer;
        } else if (normalized.equals("false") || normalized.equals("f")) {
            return !correctAnswer;
        }
        return false;
    }

    @Override
    public String getType() {
        return "TrueFalse";
    }

    public UUID getId(){
        return this.id;
    }

    @Override
    public String getCorrectAnswer() {
        return String.valueOf(correctAnswer);
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}

