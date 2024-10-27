package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a multiple choice question with several options.
 */
public class MultipleChoiceQuestion extends Question {
    private List<String> options; 
    private String correctAnswer;
    private UUID id; 
    private Word word; // Associated Word

    public MultipleChoiceQuestion(UUID id, String text, List<String> options, String correctAnswer, Word word) {
        super(text);
        this.id = id;
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
        this.word = word;
    }
    public MultipleChoiceQuestion(String text, List<String> options, String correctAnswer) {
        super(text);
        this.id = UUID.randomUUID();
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
    }
    public MultipleChoiceQuestion(UUID id,String text, List<String> options, String correctAnswer) {
        super(text);
        this.id = id;
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
    }

    public MultipleChoiceQuestion(String text, List<String> options, String correctAnswer, Word word) {
        super(text);
        this.id = UUID.randomUUID();
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
        this.word = word;
    }

    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append(getText()).append("\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append((i + 1)).append(". ").append(options.get(i)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean validateAnswer(String userAnswer) {
        try {
            int choice = Integer.parseInt(userAnswer);
            if (choice < 1 || choice > options.size()) {
                return false;
            }
            String selectedOption = options.get(choice - 1);
            return selectedOption.equalsIgnoreCase(correctAnswer);
        } catch (NumberFormatException e) {
            // User may input the option text directly
            return correctAnswer.equalsIgnoreCase(userAnswer.trim());
        }
    }

    // Getters and Setters

    public List<String> getOptions() {
        return new ArrayList<>(options);
    }

    public UUID getId(){
        return this.id;
    }

    public void setOptions(List<String> options) {
        this.options = new ArrayList<>(options);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String getType() {
        return "MultipleChoice";
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
