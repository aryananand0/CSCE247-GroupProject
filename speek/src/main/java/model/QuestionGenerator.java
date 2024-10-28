package model;

import java.util.*;

/**
 * Responsible for generating different types of questions based on a list of words.
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
     * Generates a fixed set of questions (7 questions) ensuring at least one of each type.
     *
     * @param wordList The list of Word objects to generate questions from.
     * @return A list of exactly 7 generated Question objects.
     */
    public List<Question> generateFixedQuestionSet(List<Word> wordList) {
        int fixedQuestionCount = 7;
        return generateQuestionSet(wordList, fixedQuestionCount);
    }

    /**
     * Generates a set of questions based on the number of words, ensuring at least one of each question type if possible.
     *
     * @param wordList The list of Word objects to generate questions from.
     * @return A list of generated Question objects matching the number of words.
     */
    public List<Question> generateQuestionsAsPerWords(List<Word> wordList) {
        int wordCount = wordList.size();
        return generateQuestionSet(wordList, wordCount);
    }

    /**
     * Core method for generating a set of questions based on the desired count, ensuring each question type is represented.
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
            for (int i = 0; i < requiredTypes; i++) {
                Word word = shuffledWords.get(i);
                String questionType = QUESTION_TYPES.get(i);
                generatedQuestions.add(createQuestionOfType(questionType, word, wordList));
            }

            for (int i = requiredTypes; i < totalCount; i++) {
                Word word = shuffledWords.get(i % shuffledWords.size());
                String questionType = QUESTION_TYPES.get(random.nextInt(requiredTypes));
                generatedQuestions.add(createQuestionOfType(questionType, word, wordList));
            }
        } else {
            for (int i = 0; i < totalCount; i++) {
                Word word = shuffledWords.get(i);
                String questionType = QUESTION_TYPES.get(random.nextInt(requiredTypes));
                generatedQuestions.add(createQuestionOfType(questionType, word, wordList));
            }
        }
        return generatedQuestions;
    }

    /**
     * Creates a question of the specified type for a given word.
     *
     * @param questionType The type of question to create.
     * @param word         The Word object for the question.
     * @param wordList     The complete list of words for incorrect options.
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
                if (wordList.size() >= 5) {
                    return generateMatchWordsQuestion(getRandomSubset(wordList, 5), wordList);
                } else {
                    return generateShortAnswerQuestion(word);
                }
            default:
                return generateShortAnswerQuestion(word);
        }
    }

    /**
     * Generates a Short Answer question for a given word.
     *
     * @param word The Word object.
     * @return A ShortAnswerQuestion object.
     */
    private ShortAnswerQuestion generateShortAnswerQuestion(Word word) {
        String questionText = "Translate the following word to English: \"" + word.getWord() + "\"";
        return new ShortAnswerQuestion(UUID.randomUUID(), questionText, word.getTranslation(), word);
    }

    /**
     * Generates a Multiple Choice question with options, including correct and incorrect answers.
     *
     * @param word     The Word object.
     * @param wordList The complete list of words.
     * @return A MultipleChoiceQuestion object.
     */
    private MultipleChoiceQuestion generateMultipleChoiceQuestion(Word word, List<Word> wordList) {
        String questionText = "What is the correct translation of \"" + word.getWord() + "\"?";
        List<String> options = new ArrayList<>(List.of(word.getTranslation()));

        List<Word> shuffledWordList = new ArrayList<>(wordList);
        Collections.shuffle(shuffledWordList);
        for (Word w : shuffledWordList) {
            if (!w.getTranslation().equalsIgnoreCase(word.getTranslation()) && !options.contains(w.getTranslation())) {
                options.add(w.getTranslation());
            }
            if (options.size() == 4) break;
        }

        while (options.size() < 4) options.add(word.getTranslation());

        Collections.shuffle(options);
        return new MultipleChoiceQuestion(UUID.randomUUID(), questionText, options, word.getTranslation(), word);
    }

    /**
     * Generates a True/False question for a given word.
     *
     * @param word     The Word object.
     * @param wordList The complete list of words.
     * @return A TrueFalseQuestion object.
     */
    private TrueFalseQuestion generateTrueFalseQuestion(Word word, List<Word> wordList) {
        boolean isCorrect = random.nextBoolean();
        String presentedTranslation = isCorrect ? word.getTranslation() : getRandomIncorrectTranslation(word, wordList);
        String questionText = "True or False: \"" + word.getWord() + "\" translates to \"" + presentedTranslation + "\".";
        return new TrueFalseQuestion(UUID.randomUUID(), questionText, isCorrect, word);
    }

    /**
     * Generates a Match the Following question using a subset of words.
     *
     * @param wordsToMatch The subset of Word objects.
     * @param wordList     The complete list of words.
     * @return A MatchWordsQuestion object.
     */
    private MatchWordsQuestion generateMatchWordsQuestion(List<Word> wordsToMatch, List<Word> wordList) {
        List<String> prompts = new ArrayList<>();
        List<String> responses = new ArrayList<>();
        Map<String, String> correctMatches = new HashMap<>();

        for (Word word : wordsToMatch) {
            prompts.add(word.getWord());
            responses.add(word.getTranslation());
            correctMatches.put(word.getWord(), word.getTranslation());
        }

        List<String> shuffledResponses = new ArrayList<>(responses);
        Collections.shuffle(shuffledResponses);

        if (isPerfectMatch(shuffledResponses, responses) && shuffledResponses.size() > 1) {
            Collections.swap(shuffledResponses, 0, 1);
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
     * Helper to get a random subset of words.
     *
     * @param wordList The list of words.
     * @param count    The desired count.
     * @return A subset list.
     */
    private List<Word> getRandomSubset(List<Word> wordList, int count) {
        List<Word> shuffled = new ArrayList<>(wordList);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }

    /**
     * Returns a random incorrect translation from the word list for True/False questions.
     *
     * @param word     The word to avoid.
     * @param wordList The list of words.
     * @return A random incorrect translation.
     */
    private String getRandomIncorrectTranslation(Word word, List<Word> wordList) {
        return wordList.stream()
                .filter(w -> !w.getTranslation().equalsIgnoreCase(word.getTranslation()))
                .map(Word::getTranslation)
                .findAny()
                .orElse(word.getTranslation());
    }

    /**
     * Checks if shuffled responses match the correct order.
     *
     * @param shuffledResponses The shuffled list.
     * @param correctResponses  The correct list.
     * @return True if all match, otherwise false.
     */
    private boolean isPerfectMatch(List<String> shuffledResponses, List<String> correctResponses) {
        for (int i = 0; i < correctResponses.size(); i++) {
            if (!shuffledResponses.get(i).equalsIgnoreCase(correctResponses.get(i))) {
                return false;
            }
        }
        return true;
    }
}
