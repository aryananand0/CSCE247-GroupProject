package model;

import java.util.List;

public class TestWordList {

    public static void main(String[] args) {
        // Create an instance of WordList
        WordList wordList = WordList.getInstance();
        
        // Display all words loaded from DataLoader
        System.out.println("=== Displaying All Words ===");
        displayWords(wordList);

        // Test checking if a word exists
        System.out.println("\n=== Testing Have Word ===");
        String testWord = "Hola"; // Replace with a word you expect to be in the JSON
        boolean exists = wordList.haveWord(testWord);
        System.out.println("Does '" + testWord + "' exist? " + (exists ? "Yes" : "No"));

        // Test retrieving a word by its text
        System.out.println("\n=== Testing Get Word ===");
        Word retrievedWord = wordList.getWordbyID(testWord);
        if (retrievedWord != null) {
            System.out.println("Retrieved Word: " + retrievedWord.getWord() + ", Translation: " + retrievedWord.getTranslation());
        } else {
            System.out.println("Word '" + testWord + "' not found.");
        }

        // Test getting the total number of words
        System.out.println("\n=== Testing Get Total Words ===");
        int totalWords = wordList.getTotalWords();
        System.out.println("Total Words Loaded: " + totalWords);
    }

    // Helper method to display words in the word list
    private static void displayWords(WordList wordList) {
        List<Word> words = wordList.getWords();
        if (words.isEmpty()) {
            System.out.println("No words available.");
        } else {
            System.out.println("Words in the list:");
            for (Word word : words) {
                System.out.println("  Word: " + word.getWord() + ", Translation: " + word.getTranslation());
            }
        }
    }
}
