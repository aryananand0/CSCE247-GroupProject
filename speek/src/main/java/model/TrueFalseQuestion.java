package model;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a True/False question.
 */
public class TrueFalseQuestion extends Question {
    private boolean correctAnswer; // The correct answer for this question
    private UUID id; // Unique identifier
    private Word word; // Associated Word object

    /**
     * Constructor initializing all fields including a specific UUID.
     *
     * @param id            Unique identifier of the question.
     * @param text          The text of the question.
     * @param correctAnswer The correct answer (true or false).
     * @param word          The associated Word object.
     */
    public TrueFalseQuestion(UUID id, String text, boolean correctAnswer, Word word) {
        super(text);
        this.id = id;
        this.correctAnswer = correctAnswer;
        this.word = word;
    }

    /**
     * Constructor with a specific UUID.
     */
    public TrueFalseQuestion(UUID id, String text, boolean correctAnswer) {
        super(text);
        this.id = id;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Constructor generating a random UUID.
     */
    public TrueFalseQuestion(String text, boolean correctAnswer) {
        super(text);
        this.id = UUID.randomUUID();
        this.correctAnswer = correctAnswer;
    }

    /**
     * Constructor with an associated Word object, generating a random UUID.
     */
    public TrueFalseQuestion(String text, boolean correctAnswer, Word word) {
        super(text);
        this.id = UUID.randomUUID();
        this.correctAnswer = correctAnswer;
        this.word = word;
    }

    /**
     * Displays the question text with "(True/False)".
     *
     * @return The question text.
     */
    @Override
    public String display() {
        return getText() + " (True/False)";
    }

    /**
     * Validates the user's answer by comparing it to the correct answer.
     *
     * @param userAnswer The user's provided answer.
     * @return True if correct, else false.
     */
    @Override
    public boolean validateAnswer(String userAnswer) {
        if (userAnswer == null) return false;
        String normalized = userAnswer.trim().toLowerCase();
        return (normalized.equals("true") || normalized.equals("t")) == correctAnswer;
    }

    /**
     * Retrieves the type of question as a string.
     *
     * @return "TrueFalse" indicating the question type.
     */
    @Override
    public String getType() {
        return "TrueFalse";
    }

    /**
     * Retrieves the unique identifier of the question.
     *
     * @return The UUID of the question.
     */
    @Override
    public UUID getId() {
        return this.id;
    }

    /**
     * Retrieves the correct answer for the question as a string.
     *
     * @return "true" or "false" based on the correct answer.
     */
    @Override
    public String getCorrectAnswer() {
        return String.valueOf(correctAnswer);
    }

    /**
     * Retrieves the list of associated Word objects.
     *
     * @return A read-only list containing the associated Word object, if any.
     */
    @Override
    public List<Word> getWords() {
        return word != null ? Collections.singletonList(word) : Collections.emptyList();
    }

    /**
     * Sets the correct answer.
     *
     * @param correctAnswer The new correct answer.
     */
    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
