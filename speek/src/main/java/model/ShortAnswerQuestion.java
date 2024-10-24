package model;

import java.util.UUID;

public class ShortAnswerQuestion extends Question {
    private String correctAnswer;
    private UUID id; 
 

    
    public ShortAnswerQuestion(UUID id,String text, String correctAnswer) {
        super(text);
        this.id = id;
        this.correctAnswer = correctAnswer;
    }

    public ShortAnswerQuestion(String text, String correctAnswer) {
        super(text);
        this.id = UUID.randomUUID();
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String display() {
        return getText();
    }

    @Override
    public boolean validateAnswer(String userAnswer) {
        if (userAnswer == null) return false;
        return userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public UUID getId(){
        return this.id;
    }
    @Override
    public String getType() {
        return "ShortAnswer";
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}



