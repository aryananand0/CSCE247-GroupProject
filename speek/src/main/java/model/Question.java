package model;


public abstract class Question {
    private final String id; 
    private String text; 

    
    public Question(String text) {
        this.id = java.util.UUID.randomUUID().toString();
        this.text = text;
    }

    
    public abstract String display();

    public abstract String getType();

    public abstract String getCorrectAnswer();
    
    public abstract boolean validateAnswer(String userAnswer);

    // Getters and Setters

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Question ID: " + id + "\n" + display();
    }
}
