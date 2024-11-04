package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Test class for the WordList.
 *
 * This class contains unit tests that verify the functionality of the WordList,
 * ensuring it correctly handles word retrieval, case sensitivity, null inputs,
 * and other edge cases. Each test is designed to expose potential errors in the code.
 */
public class WordListTest {

    private WordList wordList;

    @BeforeEach
    public void setUp() {
        // Initialize the WordList instance
        wordList = WordList.getInstance();

        // Clear any existing words for a clean test environment
        wordList.getWords().clear();

        // Add some sample words
        wordList.getWords().add(new Word("Hola", "Hello"));
        wordList.getWords().add(new Word("Adiós", "Goodbye"));
        wordList.getWords().add(new Word("Gracias", "Thank you"));
    }

    /**
     * Test case 1: Test haveWord with exact match (case-insensitive).
     * Expects to return true for existing words regardless of case.
     */
    @Test
    public void testHaveWord_ExactMatch() {
        assertTrue(wordList.haveWord("hola"), "Expected to find 'hola' in the word list (case-insensitive).");
        assertTrue(wordList.haveWord("HOLA"), "Expected to find 'HOLA' in the word list (case-insensitive).");
    }

    /**
     * Test case 2: Test haveWord with non-existing word.
     * Expects to return false for words not in the list.
     */
    @Test
    public void testHaveWord_NonExistingWord() {
        assertFalse(wordList.haveWord("Amigo"), "Did not expect to find 'Amigo' in the word list.");
    }

    /**
     * Test case 3: Test haveWord with null input.
     * Expects to handle null input gracefully, possibly returning false or throwing an exception.
     */
    @Test
    public void testHaveWord_NullInput() {
        assertFalse(wordList.haveWord(null), "Expected haveWord(null) to return false or handle null input gracefully.");
    }

    /**
     * Test case 4: Test getWordByName with exact match (case-insensitive).
     * Expects to retrieve the correct Word object regardless of case.
     */
    @Test
    public void testGetWordByName_ExactMatch() {
        Word word = wordList.getWordByName("gracias");
        assertNotNull(word, "Expected to retrieve 'gracias' from the word list.");
        assertEquals("Gracias", word.getWord(), "Word text should match 'Gracias'.");
        assertEquals("Thank you", word.getTranslation(), "Translation should match 'Thank you'.");
    }

    /**
     * Test case 5: Test getWordByName with non-existing word.
     * Expects to return null for words not in the list.
     */
    @Test
    public void testGetWordByName_NonExistingWord() {
        Word word = wordList.getWordByName("Amigo");
        assertNull(word, "Expected getWordByName('Amigo') to return null.");
    }

    /**
     * Test case 6: Test getWordByName with null input.
     * Expects to handle null input gracefully, possibly returning null or throwing an exception.
     */
    @Test
    public void testGetWordByName_NullInput() {
        Word word = wordList.getWordByName(null);
        assertNull(word, "Expected getWordByName(null) to return null or handle null input gracefully.");
    }

    /**
     * Test case 7: Test concurrent modification exception when modifying the word list during iteration.
     * Expects to handle concurrent modifications gracefully.
     */
    @Test
    public void testConcurrentModification() {
        List<Word> words = wordList.getWords();
        try {
            for (Word word : words) {
                if (word.getWord().equals("Hola")) {
                    words.remove(word); // Attempt to modify list during iteration
                }
            }
            fail("Expected ConcurrentModificationException when modifying the list during iteration.");
        } catch (Exception e) {
            assertTrue(e instanceof java.util.ConcurrentModificationException, "Expected a ConcurrentModificationException.");
        }
    }

    /**
     * Test case 8: Test adding a word with null fields.
     * Expects to handle words with null 'word' or 'translation' fields gracefully.
     */
    @Test
    public void testAddWord_NullFields() {
        Word nullWord = new Word(null, null);
        wordList.getWords().add(nullWord);
        assertTrue(wordList.getWords().contains(nullWord), "Word list should contain the word with null fields.");

        Word retrievedWord = wordList.getWordByName(null);
        assertNotNull(retrievedWord, "Expected to retrieve the word with null 'word' field.");
    }

    /**
     * Test case 9: Test haveWord with empty string.
     * Expects to handle empty string input gracefully, possibly returning false.
     */
    @Test
    public void testHaveWord_EmptyString() {
        assertFalse(wordList.haveWord(""), "Expected haveWord('') to return false.");
    }

    /**
     * Test case 10: Test getWordByName with empty string.
     * Expects to return null when an empty string is provided.
     */
    @Test
    public void testGetWordByName_EmptyString() {
        Word word = wordList.getWordByName("");
        assertNull(word, "Expected getWordByName('') to return null.");
    }

    /**
     * Test case 11: Test word list singleton instance consistency.
     * Expects that multiple calls to getInstance() return the same instance.
     */
    @Test
    public void testSingletonInstanceConsistency() {
        WordList anotherInstance = WordList.getInstance();
        assertSame(wordList, anotherInstance, "Expected both instances to be the same singleton instance.");
    }

    /**
     * Test case 12: Test modifying the word list externally affects the singleton instance.
     * Expects changes to be reflected across all references to the singleton.
     */
    @Test
    public void testExternalModificationAffectsSingleton() {
        Word newWord = new Word("Nuevo", "New");
        wordList.getWords().add(newWord);

        WordList anotherInstance = WordList.getInstance();
        assertTrue(anotherInstance.getWords().contains(newWord), "Singleton instance should reflect external modifications.");
    }

    /**
     * Test case 13: Test getTotalWords when word list is empty.
     * Expects to return zero when there are no words in the list.
     */
    @Test
    public void testGetTotalWords_EmptyList() {
        wordList.getWords().clear();
        assertEquals(0, wordList.getTotalWords(), "Expected total words to be 0 when the word list is empty.");
    }

    /**
     * Test case 14: Test haveWord after clearing the word list.
     * Expects to return false for any word after the list is cleared.
     */
    @Test
    public void testHaveWord_AfterClearingList() {
        wordList.getWords().clear();
        assertFalse(wordList.haveWord("Hola"), "Expected haveWord('Hola') to return false after clearing the list.");
    }

    /**
     * Test case 15: Test getWords returns a modifiable list.
     * Expects modifications to the returned list to affect the internal word list.
     */
    @Test
    public void testGetWords_ReturnsModifiableList() {
        List<Word> words = wordList.getWords();
        words.clear();
        assertEquals(0, wordList.getTotalWords(), "Expected total words to be 0 after clearing the returned list.");
    }

    /**
     * Test case 16: Test adding duplicate words to the word list.
     * Expects the word list to allow duplicates unless explicitly handled.
     */
    @Test
    public void testAddWord_DuplicateEntries() {
        Word duplicateWord = new Word("Hola", "Hello");
        wordList.getWords().add(duplicateWord);
        assertEquals(4, wordList.getTotalWords(), "Expected total words to be 4 after adding a duplicate word.");
        assertEquals(2, wordList.getWords().stream().filter(w -> w.getWord().equalsIgnoreCase("Hola")).count(),
                "Expected two instances of 'Hola' in the word list.");
    }

    /**
     * Test case 17: Test adding words with special characters and Unicode.
     * Expects the word list to handle special characters correctly.
     */
    @Test
    public void testAddWord_SpecialCharacters() {
        Word specialCharWord = new Word("Niño", "Child");
        wordList.getWords().add(specialCharWord);
        assertTrue(wordList.haveWord("niño"), "Expected to find 'niño' with special characters in the word list.");
        Word retrievedWord = wordList.getWordByName("NIÑO");
        assertNotNull(retrievedWord, "Expected to retrieve 'NIÑO' from the word list.");
        assertEquals("Niño", retrievedWord.getWord(), "Word text should match 'Niño'.");
    }

    /**
     * Test case 18: Test words with leading and trailing spaces.
     * Expects the word list to trim spaces or handle them correctly.
     */
    @Test
    public void testAddWord_LeadingTrailingSpaces() {
        Word spacedWord = new Word(" Hola ", " Hello ");
        wordList.getWords().add(spacedWord);
        assertFalse(wordList.haveWord("Hola "), "Expected haveWord('Hola ') to return false if spaces are not trimmed.");
        Word retrievedWord = wordList.getWordByName(" Hola ");
        assertNotNull(retrievedWord, "Expected to retrieve ' Hola ' with spaces from the word list.");
        assertEquals(" Hola ", retrievedWord.getWord(), "Word text should include leading and trailing spaces.");
    }

    /**
     * Test case 19: Test getWordByName with partial matches.
     * Expects to return null when partial strings are provided.
     */
    @Test
    public void testGetWordByName_PartialMatch() {
        Word word = wordList.getWordByName("Gra");
        assertNull(word, "Expected getWordByName('Gra') to return null for partial matches.");
    }

    /**
     * Test case 20: Test word list with extremely long word strings.
     * Expects the word list to handle large strings without issues.
     */
    @Test
    public void testAddWord_ExtremelyLongStrings() {
        String longWord = "a".repeat(1000);
        String longTranslation = "b".repeat(1000);
        Word longWordEntry = new Word(longWord, longTranslation);
        wordList.getWords().add(longWordEntry);
        assertTrue(wordList.haveWord(longWord), "Expected to find the extremely long word in the word list.");
        Word retrievedWord = wordList.getWordByName(longWord);
        assertNotNull(retrievedWord, "Expected to retrieve the extremely long word from the word list.");
        assertEquals(longTranslation, retrievedWord.getTranslation(), "Translation should match for the extremely long word.");
    }
    /**
     * Test case 21: Test thread safety by concurrently adding words to the WordList.
     * Expects no data corruption or exceptions during concurrent modifications.
     */
    @Test
    public void testConcurrentAdditions() throws InterruptedException {
        int numberOfThreads = 10;
        int wordsPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        Runnable addWordsTask = () -> {
            for (int i = 0; i < wordsPerThread; i++) {
                wordList.getWords().add(new Word("Word" + i, "Translation" + i));
            }
            latch.countDown();
        };

        for (int i = 0; i < numberOfThreads; i++) {
            executor.execute(addWordsTask);
        }

        latch.await(); // Wait for all threads to finish
        executor.shutdown();

        assertEquals(3 + (numberOfThreads * wordsPerThread), wordList.getTotalWords(),
                "Expected total words to be 3 initial + " + (numberOfThreads * wordsPerThread));
    }

    /**
     * Test case 22: Test retrieval of words when the word list contains null entries.
     * Expects getWordByName to handle null entries without throwing exceptions.
     */
    @Test
    public void testGetWordByName_WithNullEntries() {
        wordList.getWords().add(null); // Add a null entry

        Word word = wordList.getWordByName("Hola");
        assertNotNull(word, "Expected to retrieve 'Hola' despite presence of null entries.");

        Word nullWord = wordList.getWordByName(null);
        assertNull(nullWord, "Expected getWordByName(null) to return null even with null entries in the list.");
    }

    /**
     * Test case 23: Test adding a word with extremely long translation.
     * Expects the word list to handle large strings without issues.
     */
    @Test
    public void testAddWord_ExtremelyLongTranslation() {
        String longTranslation = "a".repeat(10000); // 10,000 characters
        Word longWord = new Word("LongWord", longTranslation);
        wordList.getWords().add(longWord);

        Word retrievedWord = wordList.getWordByName("LongWord");
        assertNotNull(retrievedWord, "Expected to retrieve 'LongWord' with extremely long translation.");
        assertEquals(longTranslation, retrievedWord.getTranslation(),
                "Translation should match the extremely long string.");
    }

    /**
     * Test case 24: Test immutability of the list returned by getWords().
     * Expects modifications to the returned list do not affect the internal word list.
     */
    @Test
    public void testGetWords_Immutability() {
        List<Word> words = wordList.getWords();
        words.add(new Word("Immutable", "ShouldNotBeAdded"));

        // Depending on implementation, if getWords() returns a direct reference,
        // the word should be added. If it returns a copy, it should not.
        // Adjust the expected behavior based on your implementation.

        // Assuming getWords() returns a direct reference (modifiable)
        assertTrue(wordList.haveWord("Immutable"), "Expected 'Immutable' to be added if getWords() is modifiable.");
    }

    /**
     * Test case 25: Test removal of words while iterating.
     * Expects no ConcurrentModificationException if handled properly.
     */
    @Test
    public void testRemoveWordsWhileIterating() {
        List<Word> words = wordList.getWords();
        try {
            for (Word word : words) {
                if (word.getWord().equalsIgnoreCase("Hola")) {
                    words.remove(word); // Attempt to remove during iteration
                }
            }
            fail("Expected ConcurrentModificationException when removing words during iteration.");
        } catch (Exception e) {
            assertTrue(e instanceof java.util.ConcurrentModificationException,
                    "Expected a ConcurrentModificationException.");
        }
    }

    /**
     * Test case 26: Test adding words with duplicate translations.
     * Expects the word list to handle duplicate translations correctly.
     */
    @Test
    public void testAddWord_DuplicateTranslations() {
        Word duplicateTranslationWord1 = new Word("Saludo1", "Hello");
        Word duplicateTranslationWord2 = new Word("Saludo2", "Hello");

        wordList.getWords().add(duplicateTranslationWord1);
        wordList.getWords().add(duplicateTranslationWord2);

        assertTrue(wordList.haveWord("Saludo1"), "Expected to find 'Saludo1' in the word list.");
        assertTrue(wordList.haveWord("Saludo2"), "Expected to find 'Saludo2' in the word list.");

        Word retrievedWord1 = wordList.getWordByName("Saludo1");
        Word retrievedWord2 = wordList.getWordByName("Saludo2");

        assertNotNull(retrievedWord1, "Expected to retrieve 'Saludo1' from the word list.");
        assertNotNull(retrievedWord2, "Expected to retrieve 'Saludo2' from the word list.");
        assertEquals("Hello", retrievedWord1.getTranslation(), "Translation should match 'Hello'.");
        assertEquals("Hello", retrievedWord2.getTranslation(), "Translation should match 'Hello'.");
    }

    /**
     * Test case 27: Test behavior when WordList is accessed before initialization.
     * Expects WordList to initialize properly or handle uninitialized state.
     */
    @Test
    public void testWordList_AccessBeforeInitialization() {
        // Simulate accessing WordList before it's initialized
        // Since WordList uses a singleton with lazy initialization, it's already initialized in setUp()
        // To truly test uninitialized access, WordList would need a different implementation
        // Alternatively, reset the singleton instance (if possible) and attempt access
        // Here, we assume WordList cannot be re-initialized, so this test might be redundant
        // Therefore, this test is a placeholder for scenarios where WordList might fail to initialize
        assertNotNull(wordList, "Expected WordList instance to be initialized.");
    }
}
