package model;

import java.util.UUID;

public abstract class Question {
    private String text; 

    
    public Question(String text) {
        this.text = text;
    }

    
    public abstract String display();

    public abstract String getType();

    public abstract String getCorrectAnswer();
    
    public abstract boolean validateAnswer(String userAnswer);

    public abstract UUID getId();

    // Getters and Setters

    

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return display();
    }
}
