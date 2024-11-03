package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the QuestionManager.
 * 
 * This class contains unit tests that verify the functionality of the QuestionManager,
 * ensuring it correctly generates questions, handles question navigation, validates answers,
 * and interacts properly with the User's missed words list.
 */
public class QuestionManagerTest {

    private QuestionManager questionManager;
    private User testUser;
    private List<Word> wordList;

    @BeforeEach
    public void setUp() {
        // Initialize a test user
        testUser = new User("testUserName", "TestFirstName", "UserLastName", "Test@email","TestPassword");

        // Initialize a word list with sample words
        wordList = Arrays.asList(
            new Word("Hola", "Hello"),
            new Word("Adiós", "Goodbye"),
            new Word("Gracias", "Thank you"),
            new Word("Por favor", "Please"),
            new Word("Perdón", "Excuse me"),
            new Word("Sí", "Yes"),
            new Word("No", "No"),
            new Word("Buenos días", "Good morning"),
            new Word("Buenas noches", "Good night"),
            new Word("Hasta luego", "See you later")
        );

        // Initialize the QuestionManager with the test user
        questionManager = new QuestionManager(testUser);
    }

    /**
     * Tests that generateFixedQuestionSet creates exactly 7 questions
     * and includes at least one question of each type.
     */
    @Test
    public void testGenerateFixedQuestionSet() {
        questionManager.generateFixedQuestionSet(wordList);
        List<Question> questions = questionManager.getAllQuestions();

        // Verify that exactly 7 questions are generated
        assertEquals(7, questions.size(), "Expected 7 questions to be generated.");

        // Verify that at least one question of each type is included
        Set<String> questionTypes = new HashSet<>();
        for (Question q : questions) {
            questionTypes.add(q.getType());
        }
        assertTrue(questionTypes.contains("ShortAnswer"), "Expected at least one ShortAnswer question.");
        assertTrue(questionTypes.contains("MultipleChoice"), "Expected at least one MultipleChoice question.");
        assertTrue(questionTypes.contains("TrueFalse"), "Expected at least one TrueFalse question.");
        assertTrue(questionTypes.contains("MatchWords"), "Expected at least one MatchWords question.");
    }

    /**
     * Tests that generateQuestionsAsPerWords creates a number of questions
     * equal to the number of words provided.
     */
    @Test
    public void testGenerateQuestionsAsPerWords() {
        questionManager.generateQuestionsAsPerWords(wordList);
        List<Question> questions = questionManager.getAllQuestions();

        // Verify that the number of questions matches the number of words
        assertEquals(wordList.size(), questions.size(), "Expected number of questions to match number of words.");
    }

    /**
     * Tests that getCurrentQuestion returns the first question after generation.
     */
    @Test
    public void testGetCurrentQuestion() {
        questionManager.generateFixedQuestionSet(wordList);
        Question currentQuestion = questionManager.getCurrentQuestion();

        // Verify that the current question is not null
        assertNotNull(currentQuestion, "Expected current question to be not null.");
    }

    /**
     * Tests the navigation through questions using moveToNextQuestion and hasNextQuestion.
     */
    @Test
    public void testQuestionNavigation() {
        questionManager.generateFixedQuestionSet(wordList);
        int totalQuestions = questionManager.getTotalQuestions();

        for (int i = 0; i < totalQuestions; i++) {
            // Verify that there is a next question
            assertTrue(questionManager.hasNextQuestion(), "Expected to have a next question.");
            questionManager.moveToNextQuestion();
        }

        // After all questions have been traversed, there should be no next question
        assertFalse(questionManager.hasNextQuestion(), "Expected no next question after traversing all questions.");
    }

    /**
     * Tests that validateCurrentAnswer correctly identifies a correct answer
     * and does not add the word to the user's missed words.
     */
    @Test
    public void testValidateCurrentAnswer_Correct() {
        questionManager.generateFixedQuestionSet(wordList);
        Question currentQuestion = questionManager.getCurrentQuestion();
        String correctAnswer = currentQuestion.getCorrectAnswer();

        boolean isCorrect = questionManager.validateCurrentAnswer(correctAnswer);

        // Verify that the answer is marked correct
        assertTrue(isCorrect, "Expected the answer to be validated as correct.");

        // Verify that the word is not added to the user's missed words
        List<Word> associatedWords = currentQuestion.getWords();
        for (Word word : associatedWords) {
            assertFalse(testUser.getMissedWords().contains(word.getWord()), "Expected the word not to be in user's missed words.");
        }
    }

    /**
     * Tests that validateCurrentAnswer correctly identifies an incorrect answer
     * and adds the word to the user's missed words.
     */
    @Test
    public void testValidateCurrentAnswer_Incorrect() {
        questionManager.generateFixedQuestionSet(wordList);
        Question currentQuestion = questionManager.getCurrentQuestion();

        // Provide an incorrect answer
        String incorrectAnswer = "Incorrect Answer";
        boolean isCorrect = questionManager.validateCurrentAnswer(incorrectAnswer);

        // Verify that the answer is marked incorrect
        assertFalse(isCorrect, "Expected the answer to be validated as incorrect.");

        // Verify that the word is added to the user's missed words
        List<Word> associatedWords = currentQuestion.getWords();
        for (Word word : associatedWords) {
            assertTrue(testUser.getMissedWords().contains(word.getWord()), "Expected the word to be in user's missed words.");
        }
    }

    /**
     * Tests that getTotalQuestions returns the correct number of questions generated.
     */
    @Test
    public void testGetTotalQuestions() {
        questionManager.generateFixedQuestionSet(wordList);
        int totalQuestions = questionManager.getTotalQuestions();

        // Verify that the total questions count is correct
        assertEquals(7, totalQuestions, "Expected total questions to be 7.");
    }

    /**
     * Tests that getCurrentQuestionIndex returns the correct index as questions are navigated.
     */
    @Test
    public void testGetCurrentQuestionIndex() {
        questionManager.generateFixedQuestionSet(wordList);

        // Initially, the index should be 0
        assertEquals(0, questionManager.getCurrentQuestionIndex(), "Expected initial question index to be 0.");

        // Move to the next question
        questionManager.moveToNextQuestion();

        // Verify that the index has incremented
        assertEquals(1, questionManager.getCurrentQuestionIndex(), "Expected question index to be 1 after moving to next question.");
    }

    /**
     * Tests that generateFixedQuestionSet throws an exception when not enough words are provided.
     */
    @Test
    public void testGenerateFixedQuestionSet_NotEnoughWords() {
        // Provide fewer words than required
        List<Word> smallWordList = wordList.subList(0, 2);

        // Verify that an exception is thrown
        assertThrows(IllegalArgumentException.class, () -> {
            questionManager.generateFixedQuestionSet(smallWordList);
        }, "Expected IllegalArgumentException when not enough words are provided.");
    }

    /**
     * Tests that generateQuestionsAsPerWords throws an exception when an empty word list is provided.
     */
    @Test
    public void testGenerateQuestionsAsPerWords_EmptyList() {
        // Provide an empty word list
        List<Word> emptyWordList = new ArrayList<>();

        // Verify that an exception is thrown
        assertThrows(IllegalArgumentException.class, () -> {
            questionManager.generateQuestionsAsPerWords(emptyWordList);
        }, "Expected IllegalArgumentException when word list is empty.");
    }

    /**
     * Tests that validateCurrentAnswer throws an exception when no question is available to validate.
     */
    @Test
    public void testValidateCurrentAnswer_NoQuestion() {
        // Do not generate any questions

        // Verify that an exception is thrown
        assertThrows(IllegalStateException.class, () -> {
            questionManager.validateCurrentAnswer("Any Answer");
        }, "Expected IllegalStateException when no question is available to validate.");
    }

    /**
     * Tests that validateCurrentAnswer returns false when a null answer is provided.
     */
    @Test
    public void testValidateCurrentAnswer_NullAnswer() {
        questionManager.generateFixedQuestionSet(wordList);

        // Verify that providing a null answer returns false
        assertFalse(questionManager.validateCurrentAnswer(null), "Expected validation to return false for null answer.");
    }

    /**
     * Tests that getAllQuestions returns all generated questions.
     */
    @Test
    public void testGetAllQuestions() {
        questionManager.generateFixedQuestionSet(wordList);
        List<Question> questions = questionManager.getAllQuestions();

        // Verify that all questions are retrieved
        assertEquals(7, questions.size(), "Expected to retrieve all 7 questions.");
    }

    /**
     * Tests that the generated questions include a variety of question types.
     */
    @Test
    public void testQuestionTypesVariety() {
        questionManager.generateFixedQuestionSet(wordList);
        List<Question> questions = questionManager.getAllQuestions();

        // Count the number of each question type
        Map<String, Integer> typeCount = new HashMap<>();
        for (Question q : questions) {
            typeCount.put(q.getType(), typeCount.getOrDefault(q.getType(), 0) + 1);
        }

        // Verify that there is at least one question of each type
        assertTrue(typeCount.keySet().size() >= 4, "Expected at least one question of each type.");
    }

    /**
     * Tests that moveToNextQuestion does not cause issues when no questions are generated.
     */
    @Test
    public void testMoveToNextQuestion_NoQuestions() {
        // Do not generate any questions

        // Attempt to move to the next question
        questionManager.moveToNextQuestion();

        // Verify that the current question remains null
        assertNull(questionManager.getCurrentQuestion(), "Expected current question to be null when no questions are generated.");
    }

    /**
     * Tests that hasNextQuestion returns false when no questions are generated.
     */
    @Test
    public void testHasNextQuestion_NoQuestions() {
        // Do not generate any questions

        // Verify that hasNextQuestion returns false
        assertFalse(questionManager.hasNextQuestion(), "Expected hasNextQuestion to return false when no questions are generated.");
    }
    /**
     * Tests that generateFixedQuestionSet handles null word list gracefully.
     * Expected to throw IllegalArgumentException.
     */
    @Test
    public void testGenerateFixedQuestionSet_NullWordList() {
        assertThrows(IllegalArgumentException.class, () -> {
            questionManager.generateFixedQuestionSet(null);
        }, "Expected IllegalArgumentException when word list is null.");
    }

    /**
     * Tests that generateQuestionsAsPerWords handles null word list gracefully.
     * Expected to throw IllegalArgumentException.
     */
    @Test
    public void testGenerateQuestionsAsPerWords_NullWordList() {
        assertThrows(IllegalArgumentException.class, () -> {
            questionManager.generateQuestionsAsPerWords(null);
        }, "Expected IllegalArgumentException when word list is null.");
    }

    /**
     * Tests that generateFixedQuestionSet handles a word list with null elements.
     * Expected to throw IllegalArgumentException.
     */
    @Test
    public void testGenerateFixedQuestionSet_WordListWithNullElements() {
        List<Word> wordListWithNulls = new ArrayList<>(wordList);
        wordListWithNulls.add(null);

        assertThrows(IllegalArgumentException.class, () -> {
            questionManager.generateFixedQuestionSet(wordListWithNulls);
        }, "Expected IllegalArgumentException when word list contains null elements.");
    }

    /**
     * Tests that generateQuestionsAsPerWords handles words with null fields.
     * Expected to throw IllegalArgumentException.
     */
    @Test
    public void testGenerateQuestionsAsPerWords_WordsWithNullFields() {
        List<Word> invalidWordList = Arrays.asList(
            new Word(null, "Hello"),
            new Word("Hola", null)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            questionManager.generateQuestionsAsPerWords(invalidWordList);
        }, "Expected IllegalArgumentException when words have null fields.");
    }

    /**
     * Tests that QuestionManager can handle duplicate words in the word list.
     */
    @Test
    public void testGenerateFixedQuestionSet_DuplicateWords() {
        List<Word> duplicateWordList = new ArrayList<>(wordList);
        duplicateWordList.add(new Word("Hola", "Hello")); // Duplicate word

        questionManager.generateFixedQuestionSet(duplicateWordList);
        List<Question> questions = questionManager.getAllQuestions();

        // Verify that questions are generated without errors
        assertEquals(7, questions.size(), "Expected 7 questions to be generated with duplicate words.");
    }

    /**
     * Tests that QuestionManager handles words with empty strings.
     * Expected to throw IllegalArgumentException.
     */
    @Test
    public void testGenerateQuestionsAsPerWords_WordsWithEmptyStrings() {
        List<Word> emptyStringWordList = Arrays.asList(
            new Word("", "Translation"),
            new Word("Word", "")
        );

        assertThrows(IllegalArgumentException.class, () -> {
            questionManager.generateQuestionsAsPerWords(emptyStringWordList);
        }, "Expected IllegalArgumentException when words have empty strings.");
    }

    /**
     * Tests initializing QuestionManager without a User object.
     * Expected to throw NullPointerException or handle gracefully.
     */
    @Test
    public void testQuestionManagerInitialization_NullUser() {
        QuestionManager qmWithoutUser = new QuestionManager(null);

        // Verify that QuestionManager can be initialized without a User
        assertNotNull(qmWithoutUser, "Expected QuestionManager to be initialized even if User is null.");
    }

    /**
     * Tests that validateCurrentAnswer handles unexpected input types.
     * Provides numeric and special character inputs.
     */
    @Test
    public void testValidateCurrentAnswer_UnexpectedInputs() {
        questionManager.generateFixedQuestionSet(wordList);
        Question currentQuestion = questionManager.getCurrentQuestion();

        // Numeric input
        boolean isCorrectNumeric = questionManager.validateCurrentAnswer("12345");
        assertFalse(isCorrectNumeric, "Expected validation to return false for numeric input.");

        // Special characters input
        boolean isCorrectSpecialChars = questionManager.validateCurrentAnswer("@#$%^&");
        assertFalse(isCorrectSpecialChars, "Expected validation to return false for special characters input.");
    }

    /**
     * Tests that QuestionManager handles moving beyond the last question without errors.
     */
    @Test
    public void testMoveBeyondLastQuestion() {
        questionManager.generateFixedQuestionSet(wordList);

        // Move through all questions
        while (questionManager.hasNextQuestion()) {
            questionManager.moveToNextQuestion();
        }

        // Attempt to move beyond the last question
        questionManager.moveToNextQuestion();

        // Verify that current question is null after moving beyond last question
        assertNull(questionManager.getCurrentQuestion(), "Expected current question to be null after moving beyond last question.");
    }

    /**
     * Tests generating questions when the word list contains only one word.
     */
    @Test
    public void testGenerateFixedQuestionSet_SingleWord() {
        List<Word> singleWordList = Collections.singletonList(new Word("Hola", "Hello"));

        assertThrows(IllegalArgumentException.class, () -> {
            questionManager.generateFixedQuestionSet(singleWordList);
        }, "Expected IllegalArgumentException when word list contains only one word.");
    }

    /**
     * Tests the behavior when generateFixedQuestionSet is called multiple times.
     */
    @Test
    public void testGenerateFixedQuestionSet_MultipleCalls() {
        questionManager.generateFixedQuestionSet(wordList);
        List<Question> firstSet = questionManager.getAllQuestions();

        // Generate another set of questions
        questionManager.generateFixedQuestionSet(wordList);
        List<Question> secondSet = questionManager.getAllQuestions();

        // Verify that a new set of questions is generated
        assertNotEquals(firstSet, secondSet, "Expected new set of questions to be different from the previous set.");
    }

    /**
     * Tests that QuestionManager handles words with Unicode characters.
     */
    @Test
    public void testGenerateQuestionsWithUnicodeWords() {
        List<Word> unicodeWordList = Arrays.asList(
            new Word("こんにちは", "Hello in Japanese"),
            new Word("안녕하세요", "Hello in Korean"),
            new Word("你好", "Hello in Chinese"),
            new Word("Привет", "Hello in Russian")
        );

        questionManager.generateQuestionsAsPerWords(unicodeWordList);
        List<Question> questions = questionManager.getAllQuestions();

        // Verify that questions are generated with Unicode characters
        assertEquals(unicodeWordList.size(), questions.size(), "Expected questions to be generated for Unicode words.");
    }

    

    /**
     * Tests that validateCurrentAnswer handles case sensitivity.
     * Expected to accept answers regardless of case.
     */
    @Test
    public void testValidateCurrentAnswer_CaseSensitivity() {
        questionManager.generateFixedQuestionSet(wordList);
        Question currentQuestion = questionManager.getCurrentQuestion();
        String correctAnswer = currentQuestion.getCorrectAnswer();

        // Provide answer in different cases
        String lowerCaseAnswer = correctAnswer.toLowerCase();
        String upperCaseAnswer = correctAnswer.toUpperCase();

        assertTrue(questionManager.validateCurrentAnswer(lowerCaseAnswer), "Expected validation to be case-insensitive (lowercase).");
        assertTrue(questionManager.validateCurrentAnswer(upperCaseAnswer), "Expected validation to be case-insensitive (uppercase).");
    }

    /**
     * Tests that validateCurrentAnswer trims whitespace in user's answer.
     * Expected to accept answers with leading/trailing whitespace.
     */
    @Test
    public void testValidateCurrentAnswer_WhitespaceHandling() {
        questionManager.generateFixedQuestionSet(wordList);
        Question currentQuestion = questionManager.getCurrentQuestion();
        String correctAnswer = currentQuestion.getCorrectAnswer();

        // Provide answer with extra whitespace
        String answerWithWhitespace = "   " + correctAnswer + "   ";

        assertTrue(questionManager.validateCurrentAnswer(answerWithWhitespace), "Expected validation to trim whitespace in user's answer.");
    }

    /**
     * Tests that getCurrentQuestion returns null when no questions have been generated.
     */
    @Test
    public void testGetCurrentQuestion_NoQuestionsGenerated() {
        // Do not generate any questions
        Question currentQuestion = questionManager.getCurrentQuestion();

        // Verify that current question is null
        assertNull(currentQuestion, "Expected current question to be null when no questions are generated.");
    }

    /**
     * Tests that the user's missed words list does not include duplicates when the same word is missed multiple times.
     */
    @Test
    public void testUserMissedWords_NoDuplicates() {
        questionManager.generateFixedQuestionSet(wordList);

        // Answer the first question incorrectly multiple times
        Question firstQuestion = questionManager.getCurrentQuestion();
        String incorrectAnswer = "Wrong Answer";

        for (int i = 0; i < 3; i++) {
            questionManager.validateCurrentAnswer(incorrectAnswer);
        }

        // Verify that the missed word is added only once to the user's missed words
        List<Word> words = firstQuestion.getWords();
        for (Word word : words) {
            int occurrences = Collections.frequency(testUser.getMissedWords(), word.getWord());
            assertEquals(1, occurrences, "Expected the word to be in user's missed words only once.");
        }
    }

    
}

