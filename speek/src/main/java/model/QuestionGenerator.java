package model;

import java.util.*;

/**
 * Responsible for generating different types of questions based on the word list.
 */
public class QuestionGenerator {
    private static final Random random = new Random();
    private static final List<String> QUESTION_TYPES = Arrays.asList(
            "ShortAnswer",
            "MultipleChoice",
            "TrueFalse",
            "MatchWords"
    );

    /**
     * Generates a fixed number of questions (7) ensuring at least one of each type.
     *
     * @param wordList The list of Word objects to generate questions from.
     * @return A list of exactly 7 generated Question objects.
     */
    public List<Question> generateFixedQuestionSet(List<Word> wordList) {
        int fixedQuestionCount = 7;
        return generateQuestionSet(wordList, fixedQuestionCount);
    }

    /**
     * Generates a number of questions equal to the number of words provided,
     * ensuring at least one of each question type if possible.
     *
     * @param wordList The list of Word objects to generate questions from.
     * @return A list of generated Question objects matching the number of words.
     */
    public List<Question> generateQuestionsAsPerWords(List<Word> wordList) {
        int wordCount = wordList.size();
        return generateQuestionSet(wordList, wordCount);
    }

    /**
     * Core method to generate a set of questions based on the desired count,
     * ensuring at least one of each question type if possible.
     *
     * @param wordList   The list of Word objects to generate questions from.
     * @param totalCount The total number of questions to generate.
     * @return A list of generated Question objects.
     */
    private List<Question> generateQuestionSet(List<Word> wordList, int totalCount) {
        if (wordList == null || wordList.isEmpty()) {
            throw new IllegalArgumentException("Word list cannot be null or empty.");
        }

        int requiredTypes = QUESTION_TYPES.size();
        List<Question> generatedQuestions = new ArrayList<>();
        List<Word> shuffledWords = new ArrayList<>(wordList);
        Collections.shuffle(shuffledWords);

        if (totalCount >= requiredTypes) {
            // Assign each question type to at least one word
            for (int i = 0; i < requiredTypes; i++) {
                Word word = shuffledWords.get(i);
                String questionType = QUESTION_TYPES.get(i);
                Question question = createQuestionOfType(questionType, word, wordList);
                generatedQuestions.add(question);
            }

            // Assign remaining words to random question types
            for (int i = requiredTypes; i < totalCount; i++) {
                Word word = shuffledWords.get(i % shuffledWords.size()); // Cycle through words if i >= wordList.size()
                String questionType = QUESTION_TYPES.get(random.nextInt(requiredTypes));
                Question question = createQuestionOfType(questionType, word, wordList);
                generatedQuestions.add(question);
            }
        } else {
            // Total questions less than required types; assign random types
            for (int i = 0; i < totalCount; i++) {
                Word word = shuffledWords.get(i);
                String questionType = QUESTION_TYPES.get(random.nextInt(requiredTypes));
                Question question = createQuestionOfType(questionType, word, wordList);
                generatedQuestions.add(question);
            }
        }

        return generatedQuestions;
    }

    /**
     * Creates a question of a specified type for a given word.
     *
     * @param questionType The type of question to create.
     * @param word         The Word object to create the question for.
     * @param wordList     The complete list of words to select incorrect options from.
     * @return A Question object of the specified type.
     */
    private Question createQuestionOfType(String questionType, Word word, List<Word> wordList) {
        switch (questionType) {
            case "ShortAnswer":
                return generateShortAnswerQuestion(word);
            case "MultipleChoice":
                return generateMultipleChoiceQuestion(word, wordList);
            case "TrueFalse":
                return generateTrueFalseQuestion(word, wordList);
            case "MatchWords":
                // For MatchWordsQuestion, ensure there are enough words
                // Typically requires a subset of words, e.g., 5
                if (wordList.size() >= 5) {
                    List<Word> subset = getRandomSubset(wordList, 5);
                    return generateMatchWordsQuestion(subset, wordList);
                } else {
                    // Fallback to another question type if not enough words
                    return generateShortAnswerQuestion(word);
                }
            default:
                // Fallback to ShortAnswerQuestion if unknown type
                return generateShortAnswerQuestion(word);
        }
    }

    /**
     * Generates a Short Answer question for a given word.
     *
     * @param word The Word object to create the question for.
     * @return A ShortAnswerQuestion object.
     */
    private ShortAnswerQuestion generateShortAnswerQuestion(Word word) {
        String questionText = "Translate the following word to English: \"" + word.getWord() + "\"";
        String correctAnswer = word.getTranslation();
        return new ShortAnswerQuestion(UUID.randomUUID(), questionText, correctAnswer, word);
    }

    /**
     * Generates a Multiple Choice question for a given word.
     *
     * @param word     The Word object to create the question for.
     * @param wordList The complete list of words to select incorrect options from.
     * @return A MultipleChoiceQuestion object.
     */
    private MultipleChoiceQuestion generateMultipleChoiceQuestion(Word word, List<Word> wordList) {
        String questionText = "What is the correct translation of \"" + word.getWord() + "\"?";

        List<String> options = new ArrayList<>();
        options.add(word.getTranslation());

        // Select 3 unique incorrect translations
        List<Word> shuffledWordList = new ArrayList<>(wordList);
        Collections.shuffle(shuffledWordList);
        for (Word w : shuffledWordList) {
            if (!w.getTranslation().equalsIgnoreCase(word.getTranslation()) && !options.contains(w.getTranslation())) {
                options.add(w.getTranslation());
            }
            if (options.size() == 4) break;
        }

        // If not enough unique incorrect options, duplicate correct answer
        while (options.size() < 4) {
            options.add(word.getTranslation());
        }

        Collections.shuffle(options); // Shuffle options

        return new MultipleChoiceQuestion(UUID.randomUUID(), questionText, options, word.getTranslation(), word);
    }

    /**
     * Generates a True/False question for a given word.
     *
     * @param word     The Word object to create the question for.
     * @param wordList The complete list of words to select incorrect translations from.
     * @return A TrueFalseQuestion object.
     */
    private TrueFalseQuestion generateTrueFalseQuestion(Word word, List<Word> wordList) {
        boolean isCorrect = random.nextBoolean();
        String presentedTranslation;

        if (isCorrect) {
            presentedTranslation = word.getTranslation();
        } else {
            // Select a random incorrect translation
            List<String> incorrectTranslations = new ArrayList<>();
            for (Word w : wordList) {
                if (!w.getTranslation().equalsIgnoreCase(word.getTranslation())) {
                    incorrectTranslations.add(w.getTranslation());
                }
            }
            if (incorrectTranslations.isEmpty()) {
                // Fallback to correct translation if no incorrect options
                presentedTranslation = word.getTranslation();
                isCorrect = true;
            } else {
                Collections.shuffle(incorrectTranslations);
                presentedTranslation = incorrectTranslations.get(0);
            }
        }

        String questionText = "True or False: \"" + word.getWord() + "\" translates to \"" + presentedTranslation + "\".";
        return new TrueFalseQuestion(UUID.randomUUID(), questionText, isCorrect, word);
    }

    /**
     * Generates a Match the Following question using a subset of words.
     *
     * @param wordsToMatch The subset of Word objects to include in the matching.
     * @param wordList     The complete list of words to ensure uniqueness.
     * @return A MatchWordsQuestion object.
     */
    private MatchWordsQuestion generateMatchWordsQuestion(List<Word> wordsToMatch, List<Word> wordList) {
        List<String> prompts = new ArrayList<>();
        List<String> responses = new ArrayList<>();

        for (Word word : wordsToMatch) {
            prompts.add(word.getWord());
            responses.add(word.getTranslation());
        }

        // Shuffle responses to create mismatches
        List<String> shuffledResponses = new ArrayList<>(responses);
        Collections.shuffle(shuffledResponses);

        // Ensure at least one mismatch
        boolean hasMismatch = false;
        for (int i = 0; i < responses.size(); i++) {
            if (!responses.get(i).equalsIgnoreCase(shuffledResponses.get(i))) {
                hasMismatch = true;
                break;
            }
        }
        if (!hasMismatch && shuffledResponses.size() > 1) {
            // Swap first two to create a mismatch
            String temp = shuffledResponses.get(0);
            shuffledResponses.set(0, shuffledResponses.get(1));
            shuffledResponses.set(1, temp);
        }

        // Create correct matches map
        Map<String, String> correctMatches = new HashMap<>();
        for (int i = 0; i < prompts.size(); i++) {
            correctMatches.put(prompts.get(i), responses.get(i));
        }

        return new MatchWordsQuestion(
                UUID.randomUUID(),
                "Match the following words to their correct translations.",
                prompts,
                shuffledResponses,
                correctMatches,
                wordsToMatch
        );
    }

    /**
     * Retrieves a random subset of words from the provided word list.
     *
     * @param wordList The complete list of words.
     * @param count    The number of words to select.
     * @return A list containing the subset of selected words.
     */
    private List<Word> getRandomSubset(List<Word> wordList, int count) {
        List<Word> shuffled = new ArrayList<>(wordList);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }
}
