package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a lesson within a course, containing questions, words, and flashcards for language learning.
 */
public class Lesson {

    // Attributes
    private UUID lessonId;
    private String lessonTitle;
    private String content;
    private List<Question> questions;
    private Flashcard flashcard;
    private boolean isCompleted;
    private List<Word> words;  // Stores Word objects associated with the lesson

    /**
     * Constructor with title, content, and a list of questions. Generates a unique lesson ID.
     *
     * @param lessonTitle The title of the lesson.
     * @param content     The content description of the lesson.
     * @param questions   A list of questions associated with the lesson.
     */
    public Lesson(String lessonTitle, String content, List<Question> questions) {
        this.lessonId = UUID.randomUUID();
        this.lessonTitle = lessonTitle;
        this.content = content;
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
        this.isCompleted = false;
        this.words = new ArrayList<>();
    }

    /**
     * Constructor with predefined lesson ID and title.
     *
     * @param uid         The unique identifier for the lesson.
     * @param lessonName  The title of the lesson.
     */
    public Lesson(UUID uid, String lessonName) {
        this.lessonId = uid;
        this.lessonTitle = lessonName;
        this.questions = new ArrayList<>();
        this.isCompleted = false;
        this.words = new ArrayList<>();
    }

    /**
     * Constructor with predefined lesson ID, title, content, and questions.
     *
     * @param lessonId    The unique identifier for the lesson.
     * @param lessonTitle The title of the lesson.
     * @param content     The content description of the lesson.
     * @param questions   A list of questions associated with the lesson.
     */
    public Lesson(UUID lessonId, String lessonTitle, String content, List<Question> questions) {
        this.lessonId = lessonId != null ? lessonId : UUID.randomUUID();
        this.lessonTitle = lessonTitle;
        this.content = content;
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
        this.isCompleted = false;
        this.flashcard = new Flashcard();
        this.words = new ArrayList<>();
    }

    /**
     * Constructor with lesson ID, title, and content. Initializes with generated questions.
     *
     * @param lessonId    The unique identifier for the lesson.
     * @param lessonTitle The title of the lesson.
     * @param content     The content description of the lesson.
     */
    public Lesson(UUID lessonId, String lessonTitle, String content) {
        this(lessonId, lessonTitle, content, null);
        this.questions = generateQuestions();
    }

    /**
     * Default constructor that initializes an empty lesson.
     */
    public Lesson() {
        this(UUID.randomUUID(), "", "", new ArrayList<>());
    }

    /**
     * Adds a word to the lesson.
     *
     * @param word The word to add.
     */
    public void addWord(Word word) {
        if (word != null) {
            this.words.add(word);
        }
    }

    /**
     * Returns a list of all words associated with the lesson.
     *
     * @return A list of Word objects.
     */
    public ArrayList<Word> getWords() {
        return new ArrayList<>(words);
    }

    /**
     * Sets the list of words for the lesson.
     *
     * @param words The list of Word objects.
     */
    public void setWords(List<Word> words) {
        this.words = words != null ? new ArrayList<>(words) : new ArrayList<>();
    }

    /**
     * Sets the flashcard for the lesson.
     *
     * @param flashcard The Flashcard object.
     */
    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    /**
     * Retrieves the flashcard associated with the lesson.
     *
     * @return The Flashcard object.
     */
    public Flashcard getFlashcard() {
        return this.flashcard;
    }

    // Getters and Setters

    public UUID getLessonId() {
        return lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
    }

    public void addQuestion(Question newQ) {
        this.questions.add(newQ);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Generates a list of questions for the lesson based on the lesson's words.
     *
     * @return A list of Question objects.
     */
    public List<Question> generateQuestions() {
        QuestionManager qm = new QuestionManager();
        qm.generateFixedQuestionSet(this.words);
        this.questions = qm.getAllQuestions();
        return questions;
    }

    @Override
    public String toString() {
        return "Lesson ID: " + lessonId + " | Lesson Title: " + lessonTitle + " | Completion: " + (isCompleted ? "Completed" : "Incomplete");
    }
}
