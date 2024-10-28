package model;

import java.util.List;

/**
 * Manages the collection of questions, handles question navigation,
 * validates answers, and tracks missed words for a User.
 */
public class QuestionManager {
    private List<Question> questions; // List of all questions
    private int currentQuestionIndex; // Index of the current question
    private User user; // Associated User

    /**
     * Initializes the QuestionManager with a specified User.
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
     * Generates a fixed set of questions, ensuring at least one of each type.
     *
     * @param words The list of Word objects to generate questions from.
     */
    public void generateFixedQuestionSet(List<Word> words) {
        if (words.size() < QUESTION_TYPES_COUNT) {
            throw new IllegalArgumentException("Requires at least " + QUESTION_TYPES_COUNT + " words to generate a question set.");
        }
        QuestionGenerator generator = new QuestionGenerator();
        this.questions = generator.generateFixedQuestionSet(words);
        this.currentQuestionIndex = 0; // Reset to the first question
    }

    /**
     * Generates questions based on the provided words' count, ensuring question variety.
     *
     * @param words The list of Word objects to generate questions from.
     */
    public void generateQuestionsAsPerWords(List<Word> words) {
        if (words.isEmpty()) {
            throw new IllegalArgumentException("Requires at least one word to generate questions.");
        }
        QuestionGenerator generator = new QuestionGenerator();
        this.questions = generator.generateQuestionsAsPerWords(words);
        this.currentQuestionIndex = 0;
    }

    /**
     * Retrieves the current question.
     *
     * @return The current Question object or null if no questions are available.
     */
    public Question getCurrentQuestion() {
        return (questions == null || questions.isEmpty() || currentQuestionIndex >= questions.size())
                ? null : questions.get(currentQuestionIndex);
    }

    /**
     * Moves to the next question, if available.
     */
    public void moveToNextQuestion() {
        if (questions != null && currentQuestionIndex < questions.size()) {
            currentQuestionIndex++;
        }
    }

    /**
     * Checks if additional questions are available.
     *
     * @return True if more questions are available, else false.
     */
    public boolean hasNextQuestion() {
        return questions != null && currentQuestionIndex < questions.size();
    }

    /**
     * Validates the user's answer to the current question. Adds missed words if incorrect.
     *
     * @param userAnswer The answer provided by the user.
     * @return True if the answer is correct, else false.
     */
    public boolean validateCurrentAnswer(String userAnswer) {
        Question currentQuestion = getCurrentQuestion();
        if (currentQuestion == null) {
            throw new IllegalStateException("No question available to validate.");
        }

        boolean isCorrect = currentQuestion.validateAnswer(userAnswer);
        List<Word> associatedWords = currentQuestion.getWords();

        for (Word word : associatedWords) {
            if (isCorrect) {
                user.RemoveMissedWord(word.getWord());
            } else {
                user.addMissedWord(word.getWord());
            }
        }

        return isCorrect;
    }

    /**
     * Retrieves the total number of questions.
     *
     * @return The total count of questions.
     */
    public int getTotalQuestions() {
        return questions != null ? questions.size() : 0;
    }

    /**
     * Retrieves the index of the current question.
     *
     * @return The current question's index.
     */
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    /**
     * Retrieves all questions generated for this session.
     *
     * @return List of all Question objects.
     */
    public List<Question> getAllQuestions() {
        return questions;
    }

    /**
     * Returns the count of question types available.
     */
    private static final int QUESTION_TYPES_COUNT = 4; // ShortAnswer, MultipleChoice, TrueFalse, MatchWords
}
