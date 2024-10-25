package model;

import java.util.ArrayList;

public class WordList {

    // Singleton instance
    private static WordList wordList = null;
    private ArrayList<Word> words;

    // Private constructor to load words from the data source
    private WordList() {
        words = DataLoader.loadWords(); // Assuming you have a DataLoader method to load words
    }

    // Singleton pattern to ensure only one instance of WordList exists
    public static WordList getInstance() {
        if (wordList == null) {
            wordList = new WordList();
        }
        return wordList;
    }


    // Method to check if a word exists by its text
    public boolean haveWord(String wordText) {
        for (Word word : words) {
            if (word.getWord().equalsIgnoreCase(wordText)) {
                return true;
            }
        }
        return false;
    }

    // Method to get a word by its text
    public Word getWord(String wordText) {
        for (Word word : words) {
            if (word.getWord().equalsIgnoreCase(wordText)) {
                return word;
            }
        }
        return null; // Word not found
    }

    // Method to get the total number of words in the list
    public int getTotalWords() {
        return words.size();
    }

    // Method to retrieve all words (getter)
    public ArrayList<Word> getWords() {
        return this.words;
    }

    /*// Method to retrieve a word by its UUID
    public Word getWordById(String wordId) {
        for (Word word : words) {
            if (word.getId().toString().equals(wordId)) {
                return word;
            }
        }
        return null; // Word not found
    }*/
}
