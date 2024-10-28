package model;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a short answer question where the user provides a text response.
 */
public class ShortAnswerQuestion extends Question {
    private String correctAnswer; // Correct answer for the question
    private UUID id; // Unique identifier for the question
    private Word word; // Associated Word object

    /**
     * Constructor initializing all fields including a specific UUID.
     *
     * @param id            Unique identifier of the question.
     * @param text          The text of the question.
     * @param correctAnswer The correct answer to the question.
     * @param word          The associated Word object.
     */
    public ShortAnswerQuestion(UUID id, String text, String correctAnswer, Word word) {
        super(text);
        this.id = id;
        this.correctAnswer = correctAnswer;
        this.word = word;
    }

    /**
     * Constructor for initializing with an id, question text, and correct answer.
     */
    public ShortAnswerQuestion(UUID id, String text, String correctAnswer) {
        super(text);
        this.id = id;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Constructor generating a random UUID for the question.
     */
    public ShortAnswerQuestion(String text, String correctAnswer) {
        super(text);
        this.id = UUID.randomUUID();
        this.correctAnswer = correctAnswer;
    }

    /**
     * Constructor with associated Word object, generating a random UUID.
     */
    public ShortAnswerQuestion(String text, String correctAnswer, Word word) {
        super(text);
        this.id = UUID.randomUUID();
        this.correctAnswer = correctAnswer;
        this.word = word;
    }

    /**
     * Displays the question text.
     *
     * @return The text of the question.
     */
    @Override
    public String display() {
        return getText();
    }

    /**
     * Validates the user's answer by comparing it to the correct answer (case-insensitive).
     *
     * @param userAnswer The user's provided answer.
     * @return True if correct, false otherwise.
     */
    @Override
    public boolean validateAnswer(String userAnswer) {
        return userAnswer != null && userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }

    /**
     * Retrieves the correct answer for the question.
     *
     * @return The correct answer as a string.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Retrieves the unique identifier of the question.
     *
     * @return The UUID of the question.
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Returns the type of question as a string.
     *
     * @return "ShortAnswer" indicating the type of question.
     */
    @Override
    public String getType() {
        return "ShortAnswer";
    }

    /**
     * Sets the correct answer.
     *
     * @param correctAnswer The new correct answer.
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
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
}
