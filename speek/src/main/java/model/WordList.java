package model;

import java.util.ArrayList;

/**
 * Manages a list of {@link Word} objects within the system using the Singleton design pattern.
 * Provides functionalities to add, remove, and retrieve words.
 */
public class WordList {

    // Singleton instance
    private static WordList wordList = null;

    // List of all words
    private ArrayList<Word> words;

    /**
     * Private constructor to prevent external instantiation.
     * Loads words from the data source upon creation.
     */
    private WordList() {
        words = DataLoader.loadWords(); // Assuming you have a DataLoader method to load words
    }

    /**
     * Retrieves the singleton instance of {@code WordList}.
     * If the instance doesn't exist, it initializes a new one.
     *
     * @return The singleton instance of {@code WordList}.
     */
    public static WordList getInstance() {
        if (wordList == null) {
            wordList = new WordList();
        }
        return wordList;
    }

    /**
     * Checks if a word exists in the word list based on its text.
     * The comparison is case-insensitive.
     *
     * @param wordText The text of the word to check for existence.
     * @return {@code true} if the word exists; {@code false} otherwise.
     */
    public boolean haveWord(String wordText) {
        for (Word word : words) {
            if (word.getWord().equalsIgnoreCase(wordText)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the total number of words in the word list.
     *
     * @return The total number of words.
     */
    public int getTotalWords() {
        return words.size();
    }

    /**
     * Retrieves the entire list of words.
     *
     * @return An {@link ArrayList} containing all {@link Word} objects.
     */
    public ArrayList<Word> getWords() {
        return this.words;
    }

    /**
     * Retrieves a {@link Word} object based on its name.
     * The search is case-insensitive.
     *
     * @param name The name of the word to retrieve.
     * @return The {@link Word} object if found; {@code null} otherwise.
     */
    public Word getWordByName(String name) {
        for (Word word : words) {
            if (word.getWord().equalsIgnoreCase(name)) {
                return word;
            }
        }
        return null;
    }

    /*
    /**
     * Retrieves a {@link Word} object based on its UUID.
     *
     * @param wordId The UUID of the word to retrieve.
     * @return The {@link Word} object if found; {@code null} otherwise.
     */
    /*
    public Word getWordById(String wordId) {
        for (Word word : words) {
            if (word.getId().toString().equals(wordId)) {
                return word;
            }
        }
        return null; // Word not found
    }
    */
}
