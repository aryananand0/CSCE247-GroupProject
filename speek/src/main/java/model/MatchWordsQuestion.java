package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MatchWordsQuestion extends Question {
    private List<String> prompts; 
    private List<String> responses; 
    private Map<String, String> correctMatches; 

    
    public MatchWordsQuestion(String text, List<String> prompts, List<String> responses, Map<String, String> correctMatches) {
        super(text);
        this.prompts = prompts;
        this.responses = responses;
        this.correctMatches = new HashMap<>(correctMatches);
    }

    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append(getText()).append("\n");
        sb.append("Match the following:\n");
        sb.append("Prompts:\n");
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
                String prompt = prompts.get(promptIndex);
                String response = responses.get(responseIndex);
                userMatches.put(prompt, response);
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
    @Override
    public String getType() {
        return "MatchWords";
    }

    @Override
    public String getCorrectAnswer() {
        // Convert the map to a string representation
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
}