package model;
import java.util.HashMap;
import java.util.Map;

public class Flashcard {

    private HashMap<String, String> flashcards;

    // Constructor
    public Flashcard() {
        flashcards = new HashMap<>();
    }

    public Flashcard(HashMap<String, String> flashcards){
        this.flashcards = flashcards;
    }

    // Method to add a flashcard
    public void addFlashcard(String word, String translation) {
        flashcards.put(word, translation);
    }

    // Method to remove a flashcard
    public void removeFlashcard(String word) {
        flashcards.remove(word);
    }

    // Method to get the translation of a word
    public String getTranslation(String word) {
        return flashcards.getOrDefault(word, null); // Return null if word not found
    }

    // Method to return all flashcards as a map
    public Map<String, String> getAllFlashcards() {
        return new HashMap<>(flashcards); // Return a copy of the flashcards
    }

    // Method to quiz the user, returns the result of each quiz question
    public Map<String, Boolean> quizUser(Map<String, String> userResponses) {
        Map<String, Boolean> results = new HashMap<>();

        for (Map.Entry<String, String> entry : flashcards.entrySet()) {
            String userAnswer = userResponses.get(entry.getKey());
            boolean isCorrect = entry.getValue().equalsIgnoreCase(userAnswer);
            results.put(entry.getKey(), isCorrect);
        }

        return results; // Return a map of word -> whether the user's answer was correct
    }

    // Method to return the total number of flashcards
    public int getTotalFlashcards() {
        return flashcards.size();
    }

    // Method to get flashcards for study, returns the flashcard in a form for learning (word -> translation)
    public Map<String, String> getFlashcardsForStudy() {
        return new HashMap<>(flashcards);
    }

    // Method to check if there are any flashcards
    public boolean hasFlashcards() {
        return !flashcards.isEmpty();
    }

    // Method to clear all flashcards
    public void clearAllFlashcards() {
        flashcards.clear();
    }
}
