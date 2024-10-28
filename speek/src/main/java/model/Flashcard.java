package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.narration.Narriator;

/**
 * Flashcard class to manage flashcards for studying and quizzing.
 * Provides functionalities for adding, removing, displaying, and quizzing flashcards.
 */
public class Flashcard {

    private HashMap<String, String> flashcards;

    /**
     * Default constructor to initialize an empty flashcard collection.
     */
    public Flashcard() {
        flashcards = new HashMap<>();
    }

    /**
     * Constructor to initialize flashcards with a given map of word-translation pairs.
     *
     * @param flashcards Initial flashcards to add.
     */
    public Flashcard(HashMap<String, String> flashcards) {
        this.flashcards = new HashMap<>(flashcards);
    }

    /**
     * Adds a flashcard with a word and its translation.
     *
     * @param word        Word to add.
     * @param translation Translation for the word.
     */
    public void addFlashcard(String word, String translation) {
        flashcards.put(word, translation);
    }

    /**
     * Removes a flashcard based on the word.
     *
     * @param word Word of the flashcard to remove.
     */
    public void removeFlashcard(String word) {
        flashcards.remove(word);
    }

    /**
     * Retrieves the translation for a specific word.
     *
     * @param word Word to translate.
     * @return Translation of the word or null if not found.
     */
    public String getTranslation(String word) {
        return flashcards.getOrDefault(word, null);
    }

    /**
     * Retrieves all flashcards as an unmodifiable map.
     *
     * @return Map of all flashcards (word -> translation).
     */
    public Map<String, String> getAllFlashcards() {
        return Collections.unmodifiableMap(flashcards);
    }

    /**
     * Quizzes the user with flashcards and checks the answers.
     *
     * @param userResponses User-provided answers in a map (word -> user answer).
     * @return Map indicating whether each answer was correct (word -> isCorrect).
     */
    public Map<String, Boolean> quizUser(Map<String, String> userResponses) {
        Map<String, Boolean> results = new HashMap<>();

        for (Map.Entry<String, String> entry : flashcards.entrySet()) {
            String userAnswer = userResponses.get(entry.getKey());
            results.put(entry.getKey(), entry.getValue().equalsIgnoreCase(userAnswer));
        }
        return results;
    }

    /**
     * Returns the total number of flashcards.
     *
     * @return Total count of flashcards.
     */
    public int getTotalFlashcards() {
        return flashcards.size();
    }

    /**
     * Checks if there are any flashcards in the collection.
     *
     * @return true if there are flashcards, false otherwise.
     */
    public boolean hasFlashcards() {
        return !flashcards.isEmpty();
    }

    /**
     * Clears all flashcards from the collection.
     */
    public void clearAllFlashcards() {
        flashcards.clear();
    }

    /**
     * Sequentially displays flashcards, waiting for user input to view each translation.
     */
    public void showFlashcardsSequentially() {
        if (flashcards.isEmpty()) {
            System.out.println("No flashcards available to display.");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            int count = 1;
            int total = flashcards.size();

            for (Map.Entry<String, String> entry : flashcards.entrySet()) {
                System.out.println("Flashcard " + count + " of " + total + ":");
                System.out.println("Word: " + entry.getKey());
                Narriator.playSound(entry.getKey());
                System.out.println("Press Enter to see the translation...");
                scanner.nextLine();
                System.out.println("Translation: " + entry.getValue());
                Narriator.playSound(entry.getValue());
                System.out.println("------------------------------------");
                count++;
            }
            System.out.println("You have reviewed all flashcards.");
        }
    }

    /**
     * Timed display of flashcards with a 7-second interval, clearing the screen for each flashcard.
     */
    public void showFlashcardsTimed() {
        if (flashcards.isEmpty()) {
            System.out.println("No flashcards available to display.");
            return;
        }

        List<Map.Entry<String, String>> flashcardList = new ArrayList<>(flashcards.entrySet());
        Collections.shuffle(flashcardList);

        int done = 0;
        int total = flashcardList.size();

        for (Map.Entry<String, String> entry : flashcardList) {
            done++;
            clearTerminal();

            System.out.println("Flashcards Done: " + done + " | Flashcards Left: " + (total - done));
            System.out.println("====================================");
            System.out.println("Word: " + entry.getKey());
            Narriator.playSound(entry.getKey());
            System.out.println("====================================");
            System.out.println("Translation: " + entry.getValue());
            Narriator.playSound(entry.getValue());

            try {
                Thread.sleep(7000); // 7 seconds
            } catch (InterruptedException e) {
                System.out.println("Flashcard display interrupted.");
                Thread.currentThread().interrupt();
                return;
            }
        }

        clearTerminal();
        System.out.println("Flashcard review completed!");
    }

    /**
     * Clears the terminal screen using ANSI codes or a command, depending on the operating system.
     */
    private void clearTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("\n".repeat(50));
        }
    }
}
