package model;

import java.util.List;
import java.util.UUID;

/**
 * Abstract base class representing a generic question. Defines the structure for various question types
 * with methods to display, validate, and retrieve details about the question.
 */
public abstract class Question {
    private String text;  // The question text

    /**
     * Constructs a Question with the given text.
     *
     * @param text The text of the question.
     */
    public Question(String text) {
        this.text = text;
    }

    /**
     * Displays the question text and any associated options or prompts, depending on the subclass.
     *
     * @return The formatted question display text.
     */
    public abstract String display();

    /**
     * Retrieves the type of the question (e.g., MultipleChoice, MatchWords).
     *
     * @return The question type as a String.
     */
    public abstract String getType();

    /**
     * Retrieves the correct answer for the question.
     *
     * @return The correct answer as a String.
     */
    public abstract String getCorrectAnswer();

    /**
     * Validates the user's answer by comparing it to the correct answer.
     *
     * @param userAnswer The answer provided by the user.
     * @return True if the answer is correct; otherwise, false.
     */
    public abstract boolean validateAnswer(String userAnswer);

    /**
     * Retrieves the unique identifier (UUID) of the question.
     *
     * @return The UUID of the question.
     */
    public abstract UUID getId();

    /**
     * Retrieves a list of Word objects associated with the question, useful for language learning applications.
     *
     * @return List of associated Word objects.
     */
    public abstract List<Word> getWords();

    // Getters and Setters

    /**
     * Gets the question text.
     *
     * @return The question text.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the question text.
     *
     * @param text The new question text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Provides a string representation of the question, typically by displaying it.
     *
     * @return The formatted question text.
     */
    @Override
    public String toString() {
        return display();
    }
}
