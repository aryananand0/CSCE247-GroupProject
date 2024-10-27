package model;

import java.util.List;
import java.util.UUID;

/**
 * Abstract base class representing a generic question.
 */
public abstract class Question {
    private String text; 

    public Question(String text) {
        this.text = text;
    }

    /**
     * Displays the question.
     *
     * @return The question text.
     */
    public abstract String display();

    /**
     * Returns the type of the question.
     *
     * @return The question type as a string.
     */
    public abstract String getType();

    /**
     * Retrieves the correct answer for the question.
     *
     * @return The correct answer as a string.
     */
    public abstract String getCorrectAnswer();

    /**
     * Validates the user's answer.
     *
     * @param userAnswer The answer provided by the user.
     * @return True if correct, else false.
     */
    public abstract boolean validateAnswer(String userAnswer);

    /**
     * Retrieves the unique identifier of the question.
     *
     * @return The UUID of the question.
     */
    public abstract UUID getId();

    /**
     * Retrieves the list of Word objects associated with this question.
     *
     * @return List of Word objects.
     */
    public abstract List<Word> getWords();

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
