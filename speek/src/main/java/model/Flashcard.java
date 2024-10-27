package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.narration.Narriator;

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

    public void showFlashcardsSequentially() {
        if (flashcards.isEmpty()) {
            System.out.println("No flashcards available to display.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int count = 1;
        int total = flashcards.size();

        for (Map.Entry<String, String> entry : flashcards.entrySet()) {
            System.out.println("Flashcard " + count + " of " + total + ":");
            System.out.println("Word: " + entry.getKey());
            System.out.println("Press Enter to see the translation...");
            scanner.nextLine(); // Wait for the user to press Enter
            System.out.println("Translation: " + entry.getValue());
            System.out.println("------------------------------------");
            count++;
        }
        scanner.close();

        System.out.println("You have reviewed all flashcards.");
    }
    public void showFlashcardsTimed() {
        if (flashcards.isEmpty()) {
            System.out.println("No flashcards available to display.");
            return;
        }

        // Convert flashcards to a list for indexed access
        List<Map.Entry<String, String>> flashcardList = new ArrayList<>(flashcards.entrySet());

        // Optionally, shuffle the flashcards for random order
        Collections.shuffle(flashcardList);

        int total = flashcardList.size();
        int done = 0;
        int left = total;

        for (Map.Entry<String, String> entry : flashcardList) {
            done++;
            left--;

            // Clear the terminal
            clearTerminal();

            // Display counts
            System.out.println("Flashcards Done: " + done + " | Flashcards Left: " + left);
            System.out.println("====================================");
            System.out.println("Word: " + entry.getKey());
            Narriator.playSound(entry.getKey());
            System.out.println("====================================");
            System.out.println("Translation: " + entry.getValue());
            Narriator.playSound(entry.getValue());


            // Wait for 7 seconds before showing the next flashcard
            try {
                Thread.sleep(7000); // 7000 milliseconds = 7 seconds
            } catch (InterruptedException e) {
                System.out.println("Flashcard display interrupted.");
                Thread.currentThread().interrupt(); // Restore the interrupted status
                return;
            }
        }

        // Final message after all flashcards have been displayed
        clearTerminal();
        System.out.println("Flashcard review completed!");
    }

    /**
     * Helper method to clear the terminal screen.
     * Uses ANSI escape codes which may not work on all operating systems or terminals.
     */
    private void clearTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // For Windows, execute 'cls' command
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix/Linux/Mac, use ANSI escape code
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing the terminal fails, fallback to printing new lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}
