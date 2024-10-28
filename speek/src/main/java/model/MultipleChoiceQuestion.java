package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a multiple-choice question with a list of options.
 */
public class MultipleChoiceQuestion extends Question {

    private List<String> options;       // List of answer options
    private String correctAnswer;       // Correct answer as a string
    private UUID id;                    // Unique identifier for the question
    private Word word;                  // Associated Word object, if applicable

    /**
     * Constructs a MultipleChoiceQuestion with specified details.
     *
     * @param id            Unique identifier of the question.
     * @param text          Text of the question.
     * @param options       List of possible answer options.
     * @param correctAnswer The correct answer among the options.
     * @param word          Associated Word object, if applicable.
     */
    public MultipleChoiceQuestion(UUID id, String text, List<String> options, String correctAnswer, Word word) {
        super(text);
        this.id = id;
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
        this.word = word;
    }

    public MultipleChoiceQuestion(String text, List<String> options, String correctAnswer) {
        this(UUID.randomUUID(), text, options, correctAnswer, null);
    }

    public MultipleChoiceQuestion(UUID id, String text, List<String> options, String correctAnswer) {
        this(id, text, options, correctAnswer, null);
    }

    public MultipleChoiceQuestion(String text, List<String> options, String correctAnswer, Word word) {
        this(UUID.randomUUID(), text, options, correctAnswer, word);
    }

    /**
     * Displays the question and options for selection.
     *
     * @return Formatted question and options string.
     */
    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append(getText()).append("\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append((i + 1)).append(". ").append(options.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Validates the user's answer by comparing it to the correct answer.
     *
     * @param userAnswer User's answer, either as an index or text.
     * @return True if the answer is correct; otherwise, false.
     */
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
            return correctAnswer.equalsIgnoreCase(userAnswer.trim());
        }
    }

    // Getters and Setters

    public List<String> getOptions() {
        return new ArrayList<>(options);
    }

    public UUID getId() {
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
