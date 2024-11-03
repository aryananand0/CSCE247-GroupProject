package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class WordListTest {

    private WordList wordList;

    @BeforeEach
    public void setUp() {
        // Since we are dealing with a Singleton, and there's no real way to reset its state between tests
        // in a production scenario without adding reset functionality to the WordList class itself,
        // we assume here that each test runs in isolation (which is generally not the case with JUnit).
        wordList = WordList.getInstance();
        wordList.getWords().clear();  // Assuming a method to clear the list for testing purposes
    }

    @AfterEach
    public void tearDown() {
        wordList.getWords().clear();  // Clear the list after each test to prevent side effects between tests.
    }

    @Test
    public void testSingletonProperty() {
        // Ensuring the singleton property holds true
        WordList anotherInstance = WordList.getInstance();
        assertSame(wordList, anotherInstance, "Both instances should be the same, confirming singleton behavior.");
    }

    @Test
    public void testAddAndGetWords() {
        // Testing addition and retrieval of words
        Word newWord = new Word("Apple");
        wordList.getWords().add(newWord);
        assertTrue(wordList.getWords().contains(newWord), "Word should be added to the list.");
    }

    @Test
    public void testHaveWordExists() {
        // Testing word existence in the list
        Word newWord = new Word("Banana");
        wordList.getWords().add(newWord);
        assertTrue(wordList.haveWord("Banana"), "The word list should confirm the existence of the word 'Banana'.");
    }

    @Test
    public void testHaveWordNotExists() {
        // Testing for a word that does not exist
        assertFalse(wordList.haveWord("Cherry"), "The word list should confirm that the word 'Cherry' does not exist.");
    }

    @Test
    public void testGetTotalWords() {
        // Testing the count of words in the list
        wordList.getWords().add(new Word("Kiwi"));
        wordList.getWords().add(new Word("Mango"));
        assertEquals(2, wordList.getTotalWords(), "Should count two words in the list.");
    }

    @Test
    public void testGetWordByNameFound() {
        // Testing retrieval of a word by name
        Word expectedWord = new Word("Lime");
        wordList.getWords().add(expectedWord);
        Word resultWord = wordList.getWordByName("Lime");
        assertEquals(expectedWord, resultWord, "The word retrieved by name should be 'Lime'.");
    }

    @Test
    public void testGetWordByNameNotFound() {
        // Testing retrieval of a word by name that does not exist
        assertNull(wordList.getWordByName("Peach"), "Retrieving a non-existent word by name should return null.");
    }
}
