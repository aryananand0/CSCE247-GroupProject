package model;


public abstract class Question {
    private final String id; 
    private String text; 

    /**
     * Constructor to initialize the question.
     *
     * @param text The text of the question.
     */
    public Question(String text) {
        this.id = java.util.UUID.randomUUID().toString();
        this.text = text;
    }

    /**
     * Displays the question in a formatted manner.
     *
     * @return The formatted question string.
     */
    public abstract String display();

    /**
     * Validates the user's answer.
     *
     * @param userAnswer The answer provided by the user.
     * @return True if the answer is correct, false otherwise.
     */
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
