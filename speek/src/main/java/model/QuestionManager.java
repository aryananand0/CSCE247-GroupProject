package model;

import java.util.List;

/**
 * Manages the collection of questions, handles question navigation,
 * validates answers, and tracks missed words through the User object.
 */
public class QuestionManager {
    private List<Question> questions; // List of all questions
    private int currentQuestionIndex; // Index of the current question
    private User user; // Associated User

    /**
     * Constructor to initialize the QuestionManager with a User.
     *
     * @param user The User object to track missed words.
     */
    public QuestionManager(User user) {
        this.user = user;
        this.currentQuestionIndex = 0;
    }

    public QuestionManager() {
        this.currentQuestionIndex = 0;
    }

    /**
     * Generates a fixed number of questions (7) ensuring at least one of each type.
     *
     * @param words The list of Word objects to generate questions from.
     */
    public void generateFixedQuestionSet(List<Word> words) {
        if (words.size() < QUESTION_TYPES_COUNT()) { // Assuming QUESTION_TYPES_COUNT() returns number of types
            throw new IllegalArgumentException("At least " + QUESTION_TYPES_COUNT() + " words are required to generate a fixed question set.");
        }
        QuestionGenerator generator = new QuestionGenerator();
        this.questions = generator.generateFixedQuestionSet(words);
        this.currentQuestionIndex = 0; // Reset to first question
    }

    /**
     * Generates questions equal to the number of words provided,
     * ensuring at least one of each question type if possible.
     *
     * @param words The list of Word objects to generate questions from.
     */
    public void generateQuestionsAsPerWords(List<Word> words) {
        if (words.size() < 1) { // Minimum one word
            throw new IllegalArgumentException("At least one word is required to generate questions.");
        }
        QuestionGenerator generator = new QuestionGenerator();
        this.questions = generator.generateQuestionsAsPerWords(words);
        this.currentQuestionIndex = 0; // Reset to first question
    }

    /**
     * Retrieves the current question.
     *
     * @return The current Question object, or null if no questions are available.
     */
    public Question getCurrentQuestion() {
        if (questions == null || questions.isEmpty() || currentQuestionIndex >= questions.size()) {
            return null;
        }
        return questions.get(currentQuestionIndex);
    }

    /**
     * Moves to the next question in the list.
     */
    public void moveToNextQuestion() {
        if (questions != null && currentQuestionIndex < questions.size()) {
            currentQuestionIndex++;
        }
    }

    /**
     * Checks if there are more questions to present.
     *
     * @return True if more questions are available, else false.
     */
    public boolean hasNextQuestion() {
        return questions != null && currentQuestionIndex < questions.size();
    }

    /**
     * Validates the user's answer to the current question.
     * If incorrect, records the associated words as missed.
     *
     * @param userAnswer The answer provided by the user.
     * @return True if the answer is correct, else false.
     */
    public boolean validateCurrentAnswer(String userAnswer) {
        Question currentQuestion = getCurrentQuestion();
        if (currentQuestion == null) {
            throw new IllegalStateException("No current question to validate.");
        }

        boolean isCorrect = currentQuestion.validateAnswer(userAnswer);
        if (!isCorrect) {
            // Add associated words to user's missed words
            List<Word> associatedWords = currentQuestion.getWords();
            for (Word word : associatedWords) {
                user.addMissedWord(word.getWord());
            }
        }
        else{
            List<Word> associatedWords = currentQuestion.getWords();
            for (Word word : associatedWords) {
                user.RemoveMissedWord(word.getWord());
            } 
        }
        return isCorrect;
    }

    /**
     * Retrieves the total number of questions.
     *
     * @return The total number of questions.
     */
    public int getTotalQuestions() {
        if (questions == null) return 0;
        return questions.size();
    }

    /**
     * Retrieves the index of the current question.
     *
     * @return The current question index.
     */
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    /**
     * Retrieves all generated questions.
     *
     * @return The list of all Question objects.
     */
    public List<Question> getAllQuestions() {
        return questions;
    }

    /**
     * Helper method to get the number of question types.
     *
     * @return Number of question types.
     */
    private int QUESTION_TYPES_COUNT() {
        return 4; // ShortAnswer, MultipleChoice, TrueFalse, MatchWords
    }
}
