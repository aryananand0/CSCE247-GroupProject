package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a 'Match the Following' question, where the user matches prompts with correct responses.
 */
public class MatchWordsQuestion extends Question {
    
    private List<String> prompts;          // List of prompts for matching
    private List<String> responses;        // List of possible response options
    private Map<String, String> correctMatches; // Correct mapping of prompts to responses
    private UUID id;                       // Unique identifier for the question
    private List<Word> words;              // Associated Word objects, if applicable

    /**
     * Constructs a MatchWordsQuestion with specified details.
     *
     * @param id             Unique identifier of the question.
     * @param text           Text of the question.
     * @param prompts        List of prompt words.
     * @param responses      List of response options.
     * @param correctMatches Map of correct prompt-response matches.
     * @param words          List of associated Word objects.
     */
    public MatchWordsQuestion(UUID id, String text, List<String> prompts, List<String> responses, Map<String, String> correctMatches, List<Word> words) {
        super(text);
        this.id = id;
        this.prompts = prompts;
        this.responses = responses;
        this.correctMatches = new HashMap<>(correctMatches);
        this.words = new ArrayList<>(words);
    }

    public MatchWordsQuestion(String text, List<String> prompts, List<String> responses, Map<String, String> correctMatches) {
        this(UUID.randomUUID(), text, prompts, responses, correctMatches, new ArrayList<>());
    }

    public MatchWordsQuestion(UUID id, String text, List<String> prompts, List<String> responses, Map<String, String> correctMatches) {
        this(id, text, prompts, responses, correctMatches, new ArrayList<>());
    }

    public MatchWordsQuestion(String text, List<String> prompts, List<String> responses, Map<String, String> correctMatches, List<Word> words) {
        this(UUID.randomUUID(), text, prompts, responses, correctMatches, words);
    }

    /**
     * Displays the question with prompts and responses, guiding the user on how to answer.
     *
     * @return Formatted string of the question for display.
     */
    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append(getText()).append("\nPrompts:\n");
        for (int i = 0; i < prompts.size(); i++) {
            sb.append((i + 1)).append(". ").append(prompts.get(i)).append("\n");
        }
        sb.append("Responses:\n");
        for (int i = 0; i < responses.size(); i++) {
            sb.append((i + 1)).append(". ").append(responses.get(i)).append("\n");
        }
        sb.append("Enter your matches in the format '1-2,3-1' where:\n");
        sb.append("- The first number is the prompt index\n");
        sb.append("- The second number is the response index\n");
        return sb.toString();
    }

    /**
     * Validates the user's answer by checking if their input matches the correct pairings.
     *
     * @param userAnswer User's answer in format 'promptIndex-responseIndex'.
     * @return True if the answer is correct; otherwise, false.
     */
    @Override
    public boolean validateAnswer(String userAnswer) {
        if (userAnswer == null || userAnswer.trim().isEmpty()) return false;

        String[] pairs = userAnswer.split(",");
        Map<String, String> userMatches = new HashMap<>();

        for (String pair : pairs) {
            String[] indices = pair.trim().split("-");
            if (indices.length != 2) return false;
            try {
                int promptIndex = Integer.parseInt(indices[0].trim()) - 1;
                int responseIndex = Integer.parseInt(indices[1].trim()) - 1;
                if (promptIndex < 0 || promptIndex >= prompts.size() || responseIndex < 0 || responseIndex >= responses.size()) {
                    return false;
                }
                userMatches.put(prompts.get(promptIndex), responses.get(responseIndex));
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return userMatches.equals(correctMatches);
    }

    // Getters and Setters

    public List<String> getPrompts() {
        return prompts;
    }

    public void setPrompts(List<String> prompts) {
        this.prompts = prompts;
    }

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(List<String> responses) {
        this.responses = responses;
    }

    public UUID getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return "MatchWords";
    }

    @Override
    public String getCorrectAnswer() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : correctMatches.entrySet()) {
            sb.append(entry.getKey()).append("->").append(entry.getValue()).append(";");
        }
        return sb.toString();
    }

    public Map<String, String> getCorrectMatches() {
        return new HashMap<>(correctMatches);
    }

    public void setCorrectMatches(Map<String, String> correctMatches) {
        this.correctMatches = new HashMap<>(correctMatches);
    }

    @Override
    public List<Word> getWords() {
        return new ArrayList<>(words);
    }
}
