package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a short answer question where the user provides a text response.
 */
public class ShortAnswerQuestion extends Question {
    private String correctAnswer;
    private UUID id; 
    private Word word; // Associated Word

    public ShortAnswerQuestion(UUID id, String text, String correctAnswer, Word word) {
        super(text);
        this.id = id;
        this.correctAnswer = correctAnswer;
        this.word = word;
    }
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

    public ShortAnswerQuestion(String text, String correctAnswer, Word word) {
        super(text);
        this.id = UUID.randomUUID();
        this.correctAnswer = correctAnswer;
        this.word = word;
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

    @Override
    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        if (word != null) {
            words.add(word);
        }
        return words;
    }
}
